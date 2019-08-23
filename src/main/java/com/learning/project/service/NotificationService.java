package com.learning.project.service;

import com.learning.project.dto.NotificationDTO;
import com.learning.project.dto.PaginationDTO;
import com.learning.project.enums.NotificationStatusEnum;
import com.learning.project.enums.NotificationTypeEnum;
import com.learning.project.exception.CustomizeErrorCode;
import com.learning.project.exception.CustomizeException;
import com.learning.project.mapper.NotificationMapper;
import com.learning.project.model.Notification;
import com.learning.project.model.NotificationExample;
import com.learning.project.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Youngz
 * @date 2019/8/19 - 21:43
 */
@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;


    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO();
        Integer totalPage;//最后一页

        //   Integer totalCount = questionMapper.countByUserId(userId);//查总页数
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        //处理手动输入，页面少于1， 显示第一页
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);
        //5*(i-1),size*(page-1)
        Integer offset = size * (page - 1);
        //每一页的列表
        //List<Question> questions = questionMapper.listByUserId(userId, offset, size);

        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        //排序
       example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        if (notifications.size() == 0) {
            return paginationDTO;
        }
/*        Set<Long> disUserId = notifications.stream().map(notify -> notify.getNotifier()).collect(Collectors.toSet());//拿到展示，去重
        List<Long> userIds = new ArrayList<>(disUserId);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        //查到的user
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));*/
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {

            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    //未读数
    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        //状态更新
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
