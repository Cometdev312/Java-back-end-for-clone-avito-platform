package sky.diplom.diplom.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import sky.diplom.diplom.dto.Role;
import sky.diplom.diplom.entity.Ads;
import sky.diplom.diplom.entity.Comment;
import sky.diplom.diplom.entity.User;

public class SecurityUtils {

    public SecurityUtils() {
    }

    public static void checkPermissionToAds(Ads ads, User user) {
        MyUserDetails userDetails = new MyUserDetails(user);

        if (!userDetails.getAuthorities().contains(Role.ADMIN) && userDetails.getId() != ads.getAuthor().getId()) {
            throw new AccessDeniedException("Чтобы изменить/удалить объявление, нужно иметь роль ADMIN или быть владельцем этого объявления");
        }
    }

    public static void checkPermissionToAdsComment(Comment adsComment, User user) {
        MyUserDetails userDetails = new MyUserDetails(user);

        if (!userDetails.getAuthorities().contains(Role.ADMIN) && userDetails.getId() != adsComment.getAuthor().getId()) {
            throw new AccessDeniedException("Чтобы изменить/удалить комментарий, нужно иметь роль ADMIN или быть владельцем этого комментария");
        }
    }
}
