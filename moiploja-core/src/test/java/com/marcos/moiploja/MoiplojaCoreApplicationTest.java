/**
 *
 */
package com.marcos.moiploja;

import com.marcos.moiploja.common.services.EmailService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

/**
 * @author Marcos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MoiplojaCoreApplication.class)
public class MoiplojaCoreApplicationTest {
    @Autowired
    DataSource dataSource;
    @Autowired
    EmailService emailService;

    @Test
    public void testDummy() throws SQLException {
        String schema = dataSource.getConnection().getCatalog();
        assertTrue("moiploja".equalsIgnoreCase(schema));
    }

    @Test
    @Ignore
    public void testSendEmail() {
        emailService.sendEmail("admin@gmail.com", "Moiploja - Test Mail", "This is a test email from Moiploja");
    }

}
