package com.demo.sendgrid.base;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.SocketUtils;

import com.demo.sendgrid.service.email.fake.FakeMailConfiguration;
import com.icegreen.greenmail.store.FolderException;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

import io.restassured.RestAssured;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@ContextConfiguration(classes = {FakeMailConfiguration.class})
public class EmailBaseIT {

    private static final String USER_PASSWORD = "Sendgrid@123";
    private static final String USER_NAME = "user.sendgrid";
    private static final String EMAIL_USER_ADDRESS = "greenmail@sendgrid.com";
    private static final int END_RANGE_EMAIL_PORT = 3555;
    private static final int INIT_RANGE_EMAIL_PORT = 3000;
    private static GreenMail greenMail;

    @LocalServerPort
    private int randomPort;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Before
    public final void setUpApiAccess() throws FolderException {
        RestAssured.port = randomPort;
        RestAssured.basePath = contextPath;
        getGreenMail().purgeEmailFromAllMailboxes();
    }

    public static GreenMail getGreenMail() {
        if (greenMail == null) {
            ServerSetup serverSetup = new ServerSetup(
                    SocketUtils.findAvailableTcpPort(INIT_RANGE_EMAIL_PORT, END_RANGE_EMAIL_PORT),
                    ServerSetup.getLocalHostAddress(),
                    ServerSetup.PROTOCOL_SMTP);
            greenMail = new GreenMail(serverSetup);
            greenMail.setUser(
                    EMAIL_USER_ADDRESS,
                    USER_NAME,
                    USER_PASSWORD);
            greenMail.start();
        }
        return greenMail;
    }
}
