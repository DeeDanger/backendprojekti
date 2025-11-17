package levykauppa.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import levykauppa.domain.Format;
import levykauppa.domain.FormatRepository;
import levykauppa.domain.Genre;
import levykauppa.domain.GenreRepository;
import levykauppa.domain.RecordRepository;
import levykauppa.domain.AppUser;
import levykauppa.domain.AppUserRepository;

@SpringBootApplication
@ComponentScan(basePackages = "levykauppa")
@EntityScan(basePackages = "levykauppa.domain")
@EnableJpaRepositories(basePackages = "levykauppa.domain")
public class LevykauppaApplication {
	public static void main(String[] args) {
		SpringApplication.run(LevykauppaApplication.class, args);
	}

	// Existing preloadData for formats and genres
	@Bean
	public CommandLineRunner preloadData(FormatRepository formatRepository, GenreRepository genreRepository,
			RecordRepository recordRepository) {
		return (args) -> {
			if (formatRepository.count() == 0) {
				formatRepository.save(new Format("CD"));
				formatRepository.save(new Format("Vinyl"));
				formatRepository.save(new Format("Digital"));
			}

			if (genreRepository.count() == 0) {
				genreRepository.save(new Genre("Rock"));
				genreRepository.save(new Genre("Metal"));
				genreRepository.save(new Genre("Pop"));
				genreRepository.save(new Genre("Electronic"));
				genreRepository.save(new Genre("Jazz"));
				genreRepository.save(new Genre("Classical"));
				genreRepository.save(new Genre("Hip Hop"));
				genreRepository.save(new Genre("Other"));
			}

			System.out.println("Preloaded Formats and Genres.");
		};
	}

	// New data loader for Users
	@Bean
	public CommandLineRunner dataLoader(AppUserRepository userRepo, BCryptPasswordEncoder encoder) {
		return (args) -> {
			if (userRepo.findByUsername("admin") == null) {
				userRepo.save(new AppUser("admin", encoder.encode("admin123"), "ROLE_ADMIN"));
			}
			if (userRepo.findByUsername("user") == null) {
				userRepo.save(new AppUser("user", encoder.encode("user123"), "ROLE_USER"));
			}
			System.out.println("Preloaded Users.");
		};
	}

}