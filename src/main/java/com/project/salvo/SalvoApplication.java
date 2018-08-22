package com.project.salvo;

import jdk.nashorn.internal.objects.Global;
import org.apache.tomcat.util.buf.B2CConverter;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.BootGlobalAuthenticationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.swing.*;
import java.util.*;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,
									  GameRepository gameRepository,
									  GamePlayerRepository gamePlayerRepository,
									  ShipRepository shipRepository,
									  SalvoRepository salvoRepository,
									  ScoreRepository scoreRepository){
		return args -> {

			Player p1 = new Player("kimmy@outlook.com", "p0k");
			Player p2 = new Player("hellokitty@kitten.com","p0h");
			Player p3 = new Player("walterbrain@bmail.com","p0w");
			Player p4 = new Player("jimmypig@gogo.com","p0j");
			Player p5 = new Player("lala@google.com","p0l");
			Player p6 = new Player("3dbrain@bmail.com","p03");

			playerRepository.save(p1);
			playerRepository.save(p2);
			playerRepository.save(p3);
			playerRepository.save(p4);
			playerRepository.save(p5);
			playerRepository.save(p6);


			Game g1 = new Game();
			Game g2 = new Game();
			Game g3 = new Game();
			Game g4 = new Game();
			Game g5 = new Game();
			Game g6 = new Game();
			Game g7 = new Game();
			Game g8 = new Game();


			gameRepository.save(g1);
			gameRepository.save(g2);
			gameRepository.save(g3);
			gameRepository.save(g4);
			gameRepository.save(g5);
			gameRepository.save(g6);
			gameRepository.save(g7);
			gameRepository.save(g8);


			GamePlayer GP1 = new GamePlayer(p1, g1);
			GamePlayer GP2 = new GamePlayer(p2, g1);
			GamePlayer GP3 = new GamePlayer(p3, g2);
			GamePlayer GP4 = new GamePlayer(p4, g2);
			GamePlayer GP5 = new GamePlayer(p5, g3);
			GamePlayer GP6 = new GamePlayer(p6, g3);
			GamePlayer GP7 = new GamePlayer(p1,	g4);
			GamePlayer GP8 = new GamePlayer(p6, g7);
			GamePlayer GP9 = new GamePlayer(p5, g8);

			gamePlayerRepository.save(GP1);
			gamePlayerRepository.save(GP2);
			gamePlayerRepository.save(GP3);
			gamePlayerRepository.save(GP4);
			gamePlayerRepository.save(GP5);
			gamePlayerRepository.save(GP6);
			gamePlayerRepository.save(GP7);
			gamePlayerRepository.save(GP8);
			gamePlayerRepository.save(GP9);



			//Ships locations
			List<String> L1 = Arrays.asList("H2", "H3", "H4");
			List<String> L2 = Arrays.asList("E1", "F1", "G1");
			List<String> L3 = Arrays.asList("B4", "B5");
			List<String> L4 = Arrays.asList("B5", "C5", "D5");
			List<String> L5 = Arrays.asList("C6", "C7");
			List<String> L6 = Arrays.asList("A2", "A3", "A4");
			List<String> L7 = Arrays.asList("G6", "H6");
			List<String> L8 = Arrays.asList("B5", "C5", "D5");
			List<String> L9 = Arrays.asList("E5", "E6","E7","E8");
			List<String> L10 = Arrays.asList("B7", "B8");


			Ship ship1 = new Ship("Destroyer", L1);
			Ship ship2 = new Ship("Submarine", L2);
			Ship ship3 = new Ship("Patrol Boat", L3);
			Ship ship4 = new Ship("Destroyer", L4);
			Ship ship5 = new Ship("Patrol Boat", L5);
			Ship ship6 = new Ship("Submarine", L6);
			Ship ship7 = new Ship("Patrol Boat", L7);
			Ship ship8 = new Ship("Destroyer", L8);
			Ship ship9 = new Ship("Battleship", L9);
			Ship ship10 = new Ship("Destroyer", L1);
			Ship ship11 = new Ship("Destroyer", L4);
			Ship ship12 = new Ship("Patrol Boat",L10);



			GP1.addShip(ship1);
			GP1.addShip(ship9);

			GP2.addShip(ship2);
			GP2.addShip(ship10);

			GP3.addShip(ship3);
			GP3.addShip(ship11);

			GP4.addShip(ship4);
			GP4.addShip(ship12);

			GP5.addShip(ship5);

			GP6.addShip(ship6);

			GP7.addShip(ship7);

			GP8.addShip(ship8);



			shipRepository.save(ship1);
			shipRepository.save(ship2);
			shipRepository.save(ship3);
			shipRepository.save(ship4);
			shipRepository.save(ship5);
			shipRepository.save(ship6);
			shipRepository.save(ship7);
			shipRepository.save(ship8);
			shipRepository.save(ship9);
			shipRepository.save(ship10);
			shipRepository.save(ship11);
			shipRepository.save(ship12);


			//Shots locations
			List<String> SL1 = Arrays.asList("H2","B6","C9","D3");
			List<String> SL2 = Arrays.asList("F4","J2","B2","A2");
			List<String> SL3 = Arrays.asList("F5","J3","B5","A1");
			List<String> SL4 = Arrays.asList("H8","J9","B3","A4");
			List<String> SL5 = Arrays.asList("H10","I7","B9","C5");
			List<String> SL6 = Arrays.asList("I8","J1","C6","H6");
			List<String> SL7 = Arrays.asList("G5","G6","G7","G10");
			List<String> SL8 = Arrays.asList("C7","F6","F9","G9");
			List<String> SL9 = Arrays.asList("A3","B3","D5","E8");
			List<String> SL10 = Arrays.asList("H3","I5","J2","J9");
			List<String> SL11 = Arrays.asList("F4","J6","B2","A2");
			List<String> SL12 = Arrays.asList("F6","I8","I2","G4");



			Salvo salvo1 = new Salvo(GP1,1,SL1);
			Salvo salvo2 = new Salvo(GP1,2,SL2);

			Salvo salvo3 = new Salvo(GP2,1,SL3);
			Salvo salvo4 = new Salvo(GP2,2,SL4);

			Salvo salvo5 = new Salvo(GP3,1,SL5);
			Salvo salvo6 = new Salvo(GP3,2,SL6);

			Salvo salvo7 = new Salvo(GP4,1,SL7);
			Salvo salvo8 = new Salvo(GP4,2,SL8);

			Salvo salvo9 = new Salvo(GP5,1,SL9);
			Salvo salvo10 = new Salvo(GP5,2,SL10);

			Salvo salvo11 = new Salvo(GP6,1,SL11);
			Salvo salvo12 = new Salvo(GP6,2,SL12);

			salvoRepository.save(salvo1);
			salvoRepository.save(salvo2);
			salvoRepository.save(salvo3);
			salvoRepository.save(salvo4);
			salvoRepository.save(salvo5);
			salvoRepository.save(salvo6);
			salvoRepository.save(salvo7);
			salvoRepository.save(salvo8);
			salvoRepository.save(salvo9);
			salvoRepository.save(salvo10);
			salvoRepository.save(salvo11);
			salvoRepository.save(salvo12);

			Score score1 = new Score(p1, g1,1.0);
			Score score2 = new Score(p2, g1,0.0);
			Score score3 = new Score(p3, g2,0.5);
			Score score4 = new Score(p4, g2,0.5);
			Score score5 = new Score(p5, g3,1.0);
			Score score6 = new Score(p6, g3,0.0);
			//Score score7 = new Score(p1, g4,0.0);

			scoreRepository.save(score1);
			scoreRepository.save(score2);
			scoreRepository.save(score3);
			scoreRepository.save(score4);
			scoreRepository.save(score5);
			scoreRepository.save(score6);
			//scoreRepository.save(score7);


		};
	}
}

@Configuration

class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

	@Autowired

	PlayerRepository playerRepository;


	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName ->{
			Player player = playerRepository.findByUserName(inputName);
				if (player != null) {
					return new User(player.getUserName(),
									player.getPassword(),
									AuthorityUtils.createAuthorityList("USER")
									);
				}
				else {
					throw new UsernameNotFoundException("Unknown user: " + inputName);
				}
		});
	}
}

@EnableWebSecurity
@Configuration

class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override

	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/admin/**").hasAuthority("ADMIN")
				.antMatchers("/**").hasAuthority("USER")
				.and()
			.formLogin()
			.and()

			//.loginPage("/login");

			.logout().logoutUrl("/logout");

		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

	}
}