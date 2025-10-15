package se331.project2.config;

// se331/project2/config/InitApp.java
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se331.project2.entity.Comment;
import se331.project2.entity.News;
import se331.project2.entity.NewsStatus;
import se331.project2.entity.Vote;
import se331.project2.entity.VoteType;
import se331.project2.repository.CommentRepository;
import se331.project2.repository.NewsRepository;
import se331.project2.repository.VoteRepository;
import se331.project2.security.user.Role;
import se331.project2.security.user.User;
import se331.project2.security.user.UserRepository;
import se331.project2.service.NewsStatusService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationRunner {

    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NewsStatusService newsStatusService;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        // กัน insert ซ้ำ ถ้ามีข่าวอยู่แล้วให้ข้าม
        if (newsRepository.count() > 0) return;

        // ---------- 1) Users ----------
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .name("FuJin")
                .surname("Diamond")
                .email("admin@example.com")
                .enabled(true)
                .profileImageUrl("https://i.pinimg.com/736x/b9/c4/7e/b9c47ef70bff06613d397abfce02c6e7.jpg")
                .role(Role.ADMIN)
                .build();

        User member = User.builder()
                .username("member")
                .password(passwordEncoder.encode("member"))
                .name("Ice")
                .surname("Madrid")
                .email("member@example.com")
                .enabled(true)
                .profileImageUrl("https://img.uefa.com/imgml/TP/players/1/2026/cutoff/250128377.webp")
                .role(Role.MEMBER)
                .build();

        User reader = User.builder()
                .username("reader")
                .password(passwordEncoder.encode("reader"))
                .name("Chef")
                .surname("Chelsea")
                .email("reader@example.com")
                .enabled(true)
                .profileImageUrl("https://media.printler.com/media/photo/212366-1.jpg?rmode=crop&width=638&height=900")
                .role(Role.READER)
                .build();

        userRepository.saveAll(List.of(admin, member, reader));

        // ---------- 2) News ----------
        News n1 = News.builder()
                .slug("mbappe-secret-meeting")
                .topic("Kylian Mbappé's Secret Meeting with Real Madrid Officials")
                .shortDetail("Rumors claim Mbappé had a private dinner in Paris.")
                .fullDetail("Multiple unverified sources allege a discreet dinner ...")
                .mainImageUrl("https://img-s-msn-com.akamaized.net/tenant/amp/entityid/AA1uUzX1.img?w=1908&h=1146&m=4&q=82")
                .status(NewsStatus.UNVERIFIED)
                .reporter(admin)
                .build();

        News n2 = News.builder()
                .slug("audi-join-f1")
                .topic("Audi expands commitment to Formula 1 with 100% takeover of Sauber")
                .shortDetail("German manufacturer Audi have moved to accelerate and expand their commitment to Formula 1 by choosing to take a 100% stake in Swiss operation Sauber, ahead of their arrival in the sport in 2026.")
                .fullDetail("In 2022, Audi – part of the Volkswagen Group – announced they would join F1 as a power unit supplier when sweeping new regulations that feature increased electrical power and advanced sustainable fuels will be introduced in two years’ time.")
                .mainImageUrl("https://emea-dam.audi.com/adobe/assets/urn:aaid:aem:58288312-b002-4033-8049-7351eb167403/as/1920x1440_Audi_F1_Header.jpg")
                .status(NewsStatus.UNVERIFIED)
                .reporter(member)
                .build();

        newsRepository.saveAll(List.of(n1, n2));

        // ---------- 3) Comments ----------
        Comment c1 = Comment.builder()
                .news(n1)
                .author(reader)
                .body("Reverse image search shows this photo is old.")
                .attachments(List.of(
                        "https://e0.365dm.com/20/07/768x432/skysports-kylian-mbappe-psg_5048945.jpg?20200724223212"
                ))
                .voteType(VoteType.NOT_FAKE)
                .build();

        Comment c2 = Comment.builder()
                .news(n1)
                .author(member)
                .body("Several journalists hinted at this move.")
                .attachments(List.of(
                        "https://media.cnn.com/api/v1/images/stellar/prod/200727111209-mbappe-injury.jpg?q=w_3400,h_2004,x_0,y_0,c_fill"
                ))
                .voteType(VoteType.FAKE)
                .build();

        Comment c3 = Comment.builder()
                .news(n2)
                .author(reader)
                .body("He posted about 'new beginnings' last week.")
                .attachments(List.of())
                .voteType(VoteType.FAKE)
                .build();

        commentRepository.saveAll(List.of(c1, c2, c3));

        newsStatusService.recalcAndUpdateStatus(n1.getId());
        newsStatusService.recalcAndUpdateStatus(n2.getId());

        System.out.println("✅ Initial data inserted: "
                + userRepository.count() + " users, "
                + newsRepository.count() + " news, "
                + commentRepository.count() + " comments.");

    }
}
