package ru.nikitin.userservice.codemark;

import org.junit.jupiter.api.Test;
import ru.nikitin.userservice.codemark.to.UserTo;
import ru.nikitin.userservice.codemark.utill.validation.ValidationUtil;

import java.util.List;

public class TestBinding {

    @Test
    public void checkUserTo() {
        UserTo userTo = new UserTo("", "", "1");
        String errors = ValidationUtil.getErrors(userTo);
    }

}
