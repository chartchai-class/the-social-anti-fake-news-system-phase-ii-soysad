package se331.project2.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se331.project2.entity.Comment;
import se331.project2.entity.News;
import se331.project2.entity.NewsStatus;
import se331.project2.entity.VoteType;
import se331.project2.repository.CommentRepository;
import se331.project2.repository.NewsRepository;
import se331.project2.security.user.Role;
import se331.project2.security.user.User;
import se331.project2.security.user.UserRepository;
import se331.project2.service.NewsStatusService;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationRunner {

    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NewsStatusService newsStatusService;

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public String generateSlug(String input) {
        if (input == null)
            throw new IllegalArgumentException("Input cannot be null");

        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH)
                .replaceAll("-{2,}", "-")
                .replaceAll("^-|-$", "");
    }

    private void createCommentsForNews(News news,
                                       int fakeCount,
                                       int realCount,
                                       List<User> users,
                                       List<Comment> allComments) {
        boolean mostlyFake = fakeCount > realCount;
        java.util.Random rand = new java.util.Random();

        int totalComments = 15;
        // สุ่มรายชื่อผู้ใช้ 15 คนที่ "ไม่ซ้ำกัน"
        List<User> shuffled = new java.util.ArrayList<>(users);
        java.util.Collections.shuffle(shuffled, rand);
        List<User> pickedAuthors = shuffled.subList(0, Math.min(totalComments, shuffled.size()));

        List<String> fakeBodies = List.of("This looks fake.", "Seems made up.", "Clearly misinformation.", "Unverified and suspicious.");
        List<String> realBodies = List.of("This seems legit.", "Confirmed by multiple outlets.", "Looks real to me.", "Verified by the source.");

        for (int i = 0; i < pickedAuthors.size(); i++) {
            User author = pickedAuthors.get(i);
            boolean isFake = rand.nextDouble() < (mostlyFake ? 0.7 : 0.3);
            String body = isFake
                    ? fakeBodies.get(rand.nextInt(fakeBodies.size()))
                    : realBodies.get(rand.nextInt(realBodies.size()));

            allComments.add(Comment.builder()
                    .news(news)
                    .author(author)              // ✅ ผู้ใช้ไม่ซ้ำในข่าวนี้
                    .body(body)
                    .attachments(java.util.Collections.emptyList())
                    .voteType(isFake ? VoteType.FAKE : VoteType.NOT_FAKE)
                    .build());
        }
    }




    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (newsRepository.count() > 0) return;

        // ---------- 1) Users ----------
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("123456"))
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
        
        List<User> extraReaders = java.util.stream.IntStream.rangeClosed(2, 13)
                .mapToObj(i -> User.builder()
                        .username("reader" + i)
                        .password(passwordEncoder.encode("reader" + i))
                        .name("Reader" + i)              // ปรับชื่อได้ตามใจ
                        .surname("User")
                        .email("reader" + i + "@example.com")
                        .enabled(true)
                        .profileImageUrl("https://picsum.photos/seed/reader" + i + "/400/600")
                        .role(Role.READER)
                        .build()
                )
                .collect(java.util.stream.Collectors.toList());


        List<User> allUsers = new java.util.ArrayList<>();
        allUsers.add(admin);
        allUsers.add(member);
        allUsers.add(reader);
        allUsers.addAll(extraReaders);
        userRepository.saveAll(allUsers);


        List<User> users = allUsers;
//        userRepository.saveAll(List.of(admin, member, reader));
//        List<User> users = List.of(admin, member, reader);

        // ---------- 2) News ----------
        List<News> allNews = new ArrayList<>();

        News n1 = News.builder()
                .topic("Kylian Mbappé's Secret Meeting with Real Madrid Officials")
                .slug(generateSlug("Kylian Mbappé's Secret Meeting with Real Madrid Officials"))
                .shortDetail("Rumors claim Mbappé had a private dinner with Madrid's president in Paris.")
                .fullDetail("Multiple unverified sources allege that Kylian Mbappé recently had a discreet dinner meeting with Florentino Pérez...")
                .mainImageUrl("https://static.independent.co.uk/2024/07/16/12/2024-07-16T110753Z_1177698858_UP1EK7G0UX3KK_RTRMADP_3_SOCCER-SPAIN-MAD-MBAPPE.jpg")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(0)) // admin
                .build();
        allNews.add(n1);

        News n2 = News.builder()
                .topic("Lewis Hamilton Considering Retirement Before 2026 Season")
                .slug(generateSlug("Lewis Hamilton Considering Retirement Before 2026 Season"))
                .shortDetail("Insider claims Hamilton might quit before the next big regulation changes.")
                .fullDetail("An anonymous paddock source suggests that Lewis Hamilton is seriously considering retiring before the 2026 Formula 1 season...")
                .mainImageUrl("https://www.italpassion.fr/wp-content/uploads/2025/02/hamilton-scuderia-ferrari.jpg")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(1)) // member
                .build();
        allNews.add(n2);

        News n3 = News.builder()
                .topic("Cristiano Ronaldo to Launch His Own Football League")
                .slug(generateSlug("Cristiano Ronaldo to Launch His Own Football League"))
                .shortDetail("Ronaldo reportedly wants to start a rival league in the Middle East.")
                .fullDetail("Social media reports claim that Cristiano Ronaldo is working with Middle Eastern investors to launch a brand-new football league...")
                .mainImageUrl("https://i.ytimg.com/vi/SfqEJ3vgJDo/maxresdefault.jpg")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(2)) // reader
                .build();
        allNews.add(n3);

        News n4 = News.builder()
                .topic("Max Verstappen Allegedly Threatens to Leave Red Bull Over Internal Disputes")
                .slug(generateSlug("Max Verstappen Allegedly Threatens to Leave Red Bull Over Internal Disputes"))
                .shortDetail("Reports suggest Verstappen had a heated argument with team management.")
                .fullDetail("According to leaked paddock gossip, Max Verstappen reportedly threatened to leave Red Bull Racing...")
                .mainImageUrl("https://cdn-wp.thesportsrush.com/2024/07/ae12ccb2-1920-1080-2024-07-21t111246.703.jpg?format=auto&w=3840&q=75")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(0)) // admin
                .build();
        allNews.add(n4);

        News n5 = News.builder()
                .topic("Lionel Messi to Co-Star in Hollywood Action Film")
                .slug(generateSlug("Lionel Messi to Co-Star in Hollywood Action Film"))
                .shortDetail("Rumors say Messi is set to appear alongside Tom Cruise in a new blockbuster.")
                .fullDetail("Entertainment blogs are circulating a story that Lionel Messi has been offered a cameo role...")
                .mainImageUrl("https://www.hollywoodreporter.com/wp-content/uploads/2024/09/LM-Headshot-2146.jpg?w=1296&h=730&crop=1")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(1)) // member
                .build();
        allNews.add(n5);

        News n6 = News.builder()
                .topic("Charles Leclerc Secretly Meets Ferrari Rivals for Contract Talks")
                .slug(generateSlug("Charles Leclerc Secretly Meets Ferrari Rivals for Contract Talks"))
                .shortDetail("Leclerc allegedly spotted in McLaren’s hospitality suite.")
                .fullDetail("Rumors suggest that Charles Leclerc was seen entering McLaren’s private hospitality suite...")
                .mainImageUrl("https://e0.365dm.com/23/06/1600x900/skysports-charles-leclerc-ferrari_6202621.jpg?20230629162331")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(2)) // reader
                .build();
        allNews.add(n6);

        News n7 = News.builder()
                .topic("Erling Haaland's Personal Chef Reveals Move to Barcelona")
                .slug(generateSlug("Erling Haaland's Personal Chef Reveals Move to Barcelona"))
                .shortDetail("Chef allegedly told friends that Haaland is relocating to Spain.")
                .fullDetail("A Spanish gossip magazine claims that Erling Haaland’s personal chef leaked information...")
                .mainImageUrl("https://wp.clutchpoints.com/wp-content/uploads/2023/06/Manchester-City-news-Erling-Haaland-diet-revealed-for-phenomenal-shape.jpg")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(0)) // admin
                .build();
        allNews.add(n7);

        News n8 = News.builder()
                .topic("Fernando Alonso to Become Team Principal After Retirement")
                .slug(generateSlug("Fernando Alonso to Become Team Principal After Retirement"))
                .shortDetail("Alonso rumored to lead Aston Martin F1 team post-retirement.")
                .fullDetail("Paddock insiders believe Fernando Alonso has expressed interest in becoming a team principal...")
                .mainImageUrl("https://www.racefans.net/wp-content/uploads/2023/09/racefansdotnet-6952684_HiRes.jpg")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(1)) // member
                .build();
        allNews.add(n8);

        News n9 = News.builder()
                .topic("Neymar Buys a Football Club in Brazil")
                .slug(generateSlug("Neymar Buys a Football Club in Brazil"))
                .shortDetail("Reports suggest Neymar purchased majority shares of a lower-division club.")
                .fullDetail("Several Brazilian news outlets report that Neymar has bought a majority stake in a second-division Brazilian club...")
                .mainImageUrl("https://ichef.bbci.co.uk/ace/standard/2048/cpsprodpb/0c9f/live/a5326120-0127-11f0-a8b1-950887ddc6e5.jpg")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(2)) // reader
                .build();
        allNews.add(n9);

        News n10 = News.builder()
                .topic("Mohamed Salah Signs Lifetime Contract with Liverpool")
                .slug(generateSlug("Mohamed Salah Signs Lifetime Contract with Liverpool"))
                .shortDetail("Rumors say Salah agreed to stay at Liverpool for his entire career.")
                .fullDetail("A viral tweet claims Mohamed Salah has signed a 'lifetime' contract with Liverpool...")
                .mainImageUrl("https://static.independent.co.uk/2024/10/03/12/538a4c74fbc4037994a48357a860d7fcY29udGVudHNlYXJjaGFwaSwxNzI4MDM3NTQ4-2.77681458.jpg")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(0)) // admin
                .build();
        allNews.add(n10);

        News n11 = News.builder()
                .topic("LeBron James Considering Retirement After Next Season")
                .slug(generateSlug("LeBron James Considering Retirement After Next Season"))
                .shortDetail("LeBron rumored to step away after year 22.")
                .fullDetail("Insiders say LeBron James is weighing retirement after his 22nd NBA season to focus on ownership ventures.")
                .mainImageUrl("https://media.cnn.com/api/v1/images/stellar/prod/230523093708-01-lebron-james-052223.jpg?c=original")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(1)) // member
                .build();
        allNews.add(n11);

        News n12 = News.builder()
                .topic("Steph Curry to Join EuroLeague Team")
                .slug(generateSlug("Steph Curry to Join EuroLeague Team"))
                .shortDetail("Rumors say Curry may play in Europe for a season.")
                .fullDetail("Speculation suggests Steph Curry is interested in a one-year EuroLeague stint before retiring.")
                .mainImageUrl("https://media.cnn.com/api/v1/images/stellar/prod/220611102335-steph-curry-finals-game-4.jpg?c=original")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(2)) // reader
                .build();
        allNews.add(n12);

        News n13 = News.builder()
                .topic("Giannis Antetokounmpo Launching Basketball Academy in Greece")
                .slug(generateSlug("Giannis Antetokounmpo Launching Basketball Academy in Greece"))
                .shortDetail("Plans for youth training center unveiled.")
                .fullDetail("Greek media report Giannis is building a basketball academy to nurture local talent.")
                .mainImageUrl("https://cdn.nba.com/teams/uploads/sites/1610612749/2025/04/2425_Thumbnails_RND1GM4_Giannis_1920x1080.jpg")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(0)) // admin
                .build();
        allNews.add(n13);

        News n14 = News.builder()
                .topic("Max Verstappen to Take a Year Off")
                .slug(generateSlug("Max Verstappen to Take a Year Off"))
                .shortDetail("F1 champion rumored to skip a season.")
                .fullDetail("Some sources claim Verstappen might take a sabbatical to focus on personal projects.")
                .mainImageUrl("https://cdn.images.express.co.uk/img/dynamic/73/1200x630/6105488.jpg")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(1)) // member
                .build();
        allNews.add(n14);

        News n15 = News.builder()
                .topic("Lewis Hamilton to Race in Le Mans")
                .slug(generateSlug("Lewis Hamilton to Race in Le Mans"))
                .shortDetail("Hamilton considering endurance racing.")
                .fullDetail("Reports suggest Hamilton will test with a Le Mans team next year.")
                .mainImageUrl("https://images.ps-aws.com/c?url=https%3A%2F%2Fd3cm515ijfiu6w.cloudfront.net%2Fwp-content%2Fuploads%2F2025%2F01%2F30154224%2Flewis-hamilton-ferrari-f1-2025-fiorano-test-garage-1320x742.jpg")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(2)) // reader
                .build();
        allNews.add(n15);

        News n16 = News.builder()
                .topic("Charles Leclerc to Open Restaurant")
                .slug(generateSlug("Charles Leclerc to Open Restaurant"))
                .shortDetail("F1 star branching into hospitality.")
                .fullDetail("Leclerc is rumored to be opening a luxury restaurant in Monaco.")
                .mainImageUrl("https://www.rollingstone.com/wp-content/uploads/2025/03/ChivasLeClercMelbourne-15.jpg")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(0)) // admin
                .build();
        allNews.add(n16);

        News n17 = News.builder()
                .topic("Luka Dončić to Star in Basketball Documentary")
                .slug(generateSlug("Luka Dončić to Star in Basketball Documentary"))
                .shortDetail("NBA star's journey to be featured.")
                .fullDetail("A sports network is producing a documentary on Luka Dončić’s career.")
                .mainImageUrl("https://www.the-sun.com/wp-content/uploads/sites/6/2023/08/luka-doncic-slovenia-seen-action-839525716.jpg?strip=all&w=960")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(1)) // member
                .build();
        allNews.add(n17);

        News n18 = News.builder()
                .topic("Kevin Durant Invests in E-Sports Team")
                .slug(generateSlug("Kevin Durant Invests in E-Sports Team"))
                .shortDetail("Durant expands business portfolio.")
                .fullDetail("KD reportedly bought a stake in a major e-sports organization.")
                .mainImageUrl("https://media.cnn.com/api/v1/images/stellar/prod/230309084607-kevin-durant-file-030323.jpg?c=original")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(2)) // reader
                .build();
        allNews.add(n18);

        News n19 = News.builder()
                .topic("Fernando Alonso to Race in Dakar Rally")
                .slug(generateSlug("Fernando Alonso to Race in Dakar Rally"))
                .shortDetail("Post-F1 challenge for Alonso.")
                .fullDetail("Alonso is said to be preparing for an entry in the Dakar Rally.")
                .mainImageUrl("https://media.formula1.com/image/upload/t_16by9North/c_lfill,w_3392/q_auto/v1740000000/trackside-images/2025/F1_Grand_Prix_of_Monaco/2216988953.webp")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(0)) // admin
                .build();
        allNews.add(n19);

        News n20 = News.builder()
                .topic("Cristiano Ronaldo to Buy Stake in Sporting CP")
                .slug(generateSlug("Cristiano Ronaldo to Buy Stake in Sporting CP"))
                .shortDetail("Ronaldo investing in boyhood club.")
                .fullDetail("Reports from Portugal say Ronaldo will acquire shares in Sporting CP.")
                .mainImageUrl("https://www.thesun.co.uk/wp-content/uploads/2022/10/ah-sport-preview-ronaldo-sporting-bench.jpg?strip=all&quality=100&w=1500&h=1000&crop=1")
                .status(NewsStatus.UNVERIFIED)
                .reporter(users.get(1)) // member
                .build();
        allNews.add(n20);

        // --- Save All News ---
        newsRepository.saveAll(allNews);

        // ---------- 3) Comments (Based on JSON vote counts) ----------
        List<Comment> allComments = new ArrayList<>();

        createCommentsForNews(n1, 10, 5, users, allComments);
        createCommentsForNews(n2, 5, 10, users, allComments);
        createCommentsForNews(n3, 10, 5, users, allComments);
        createCommentsForNews(n4, 5, 10, users, allComments);
        createCommentsForNews(n5, 10, 5, users, allComments);
        createCommentsForNews(n6, 10, 5, users, allComments);
        createCommentsForNews(n7, 10, 5, users, allComments);
        createCommentsForNews(n8, 5, 10, users, allComments);
        createCommentsForNews(n9, 5, 10, users, allComments);
        createCommentsForNews(n10, 10, 5, users, allComments);
        createCommentsForNews(n11, 5, 10, users, allComments);
        createCommentsForNews(n12, 10, 5, users, allComments);
        createCommentsForNews(n13, 5, 10, users, allComments);
        createCommentsForNews(n14, 10, 5, users, allComments);
        createCommentsForNews(n15, 5, 10, users, allComments);
        createCommentsForNews(n16, 10, 5, users, allComments);
        createCommentsForNews(n17, 10, 5, users, allComments);
        createCommentsForNews(n18, 5, 10, users, allComments);
        createCommentsForNews(n19, 10, 5, users, allComments);
        createCommentsForNews(n20, 5, 10, users, allComments);

        // --- Save All Comments ---
        commentRepository.saveAll(allComments);

        // ---------- 4) Recalculate Status for all News ----------
        System.out.println("Recalculating status for all news items...");
        for (News news : allNews) {
            newsStatusService.recalcAndUpdateStatus(news.getId());
        }
        System.out.println("Status recalculation complete.");

        // ---------- 5) Print Summary ----------
        System.out.println("Initial data inserted: "
                + userRepository.count() + " users, "
                + newsRepository.count() + " news, "
                + commentRepository.count() + " comments.");
    }
}