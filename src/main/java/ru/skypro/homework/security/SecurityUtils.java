package ru.skypro.homework.security;

import org.springframework.security.access.AccessDeniedException;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsComment;
import ru.skypro.homework.entity.User;

public class SecurityUtils {

    public SecurityUtils() {
    }

    public static void checkPermissionToAds(Ads ads, User user) {
        MyUserDetails userDetails = new MyUserDetails(user);

        if (!userDetails.getAuthorities().contains(Role.ADMIN) && userDetails.getId() != ads.getAuthor().getId()) {
            throw new AccessDeniedException("Чтобы изменить/удалить объявление, нужно иметь роль ADMIN или быть владельцем этого объявления");
        }
    }

    public static void checkPermissionToAdsComment(AdsComment adsComment, User user) {
        MyUserDetails userDetails = new MyUserDetails(user);

        if (!userDetails.getAuthorities().contains(Role.ADMIN) && userDetails.getId() != adsComment.getAuthor().getId()) {
            throw new AccessDeniedException("Чтобы изменить/удалить комментарий, нужно иметь роль ADMIN или быть владельцем этого комментария");
        }
    }
}
