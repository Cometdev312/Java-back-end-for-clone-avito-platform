package sky.diplom.diplom.security;

import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import sky.diplom.diplom.dto.Role;
import sky.diplom.diplom.entity.Ads;
import sky.diplom.diplom.entity.Comment;

import java.nio.file.AccessDeniedException;

@NoArgsConstructor
public class SecurityUtils {
    public static MyUserDetails getUserDetailsFromContext() {
        return (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static long getUserIdFromContext() {
        return getUserDetailsFromContext().getId();
    }

    public static void checkPermissionToAds(Ads ads) throws AccessDeniedException {
        MyUserDetails userDetails = getUserDetailsFromContext();
        if (!userDetails.getAuthorities().contains(Role.ADMIN) && userDetails.getId() != ads.getAuthor().getId()) {
            throw new AccessDeniedException("Чтобы изменить или удалить " +
                    "объявление необходимо иметь роль Admin, " +
                    "или являться автором этого объявления");
        }
    }

    public static void checkPermissionToComments(Comment comment) {
        MyUserDetails userDetails = getUserDetailsFromContext();
        if (!userDetails.getAuthorities().contains(Role.ADMIN) && userDetails.getId() != comment.getPk()) {

        }
    }
}
