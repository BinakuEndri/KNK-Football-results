Create database KNK_Football;

use knk_football;

Create Table Team (
	id int PRIMARY KEY unique,
    name varchar(50),
    stadium varchar(50) 
);

CREATE TABLE Player(
    id INT PRIMARY KEY unique,
    name TEXT,
    position TEXT
);

CREATE TABLE Squad (
    id INT PRIMARY KEY unique,
    team_id INT,
    FOREIGN KEY (team_id) REFERENCES Team(id)
);

CREATE TABLE Squad_Players (
    squad_id INT,
    player_id INT,
    FOREIGN KEY (squad_id) REFERENCES Squad(id),
    FOREIGN KEY (player_id) REFERENCES Player(id)
);

CREATE TABLE Matches (
    id INT PRIMARY KEY,
    home_team_id INT,
    away_team_id INT,
    home_team_goals INT,
    away_team_goals INT,
    match_date TIMESTAMP,
    FOREIGN KEY (home_team_id) REFERENCES Team(id),
    FOREIGN KEY (away_team_id) REFERENCES Team(id)
);

CREATE TABLE League (
    id INT PRIMARY KEY,
    name varchar(50)
);

CREATE TABLE League_Teams (
    league_id INT,
    team_id INT,
    FOREIGN KEY (league_id) REFERENCES League(id),
    FOREIGN KEY (team_id) REFERENCES Team(id)
);

CREATE TABLE League_Matches (
    league_id INT,
    match_id INT,
    FOREIGN KEY (league_id) REFERENCES League(id),
    FOREIGN KEY (match_id) REFERENCES Matches(id)
);


Create Table Users (
	id int primary key unique,
    fullname text,
    username varchar(50),
    password varchar(64),
    Date_Created timestamp,
    Date_Modified timestamp,
    role enum('admin','user')
);


Alter table Team 
add column year timestamp;

Create Table Favourite_teams(
	user_id INT,
    team_id INT,
	FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (team_id) REFERENCES Team(id)
);

Create Table Standings(
  id INT PRIMARY KEY,
    team_id INT,
    league_id INT,
    matches_played INT,
    wins INT,
    draws INT,
    losses INT,
    goals_for INT,
    goals_against INT,
    points INT,
    FOREIGN KEY (team_id) REFERENCES Team(id),
    FOREIGN KEY (league_id) REFERENCES League(id)
);

Create Table Season(
	id int primary key unique,
    name varchar(10),
    start date,
    end date
);

Create Table Goal(
	id int primary key unique,
    game int,
    team int,
	scored int,
    assisted int default null,
    minute timestamp,
    owngoal boolean default false,
    penalty boolean default false,
	FOREIGN KEY (team) REFERENCES Team(id),
    FOREIGN KEY (game) REFERENCES Matches(id),
	FOREIGN KEY (scored) REFERENCES Player(id),
    FOREIGN KEY (assisted) REFERENCES Player(id)
);

Create Table Match_Statistics(
	id INT PRIMARY KEY unique,
    match_id INT,
    home_team_goals INT,
    away_team_goals INT,
    possession_home DECIMAL(5,2),
    possession_away DECIMAL(5,2),
    shots_home INT,
    shots_away INT,
    corners_home INT,
    corners_away INT,
    fouls_home INT,
    fouls_away INT,
    yellow_cards_home INT,
    yellow_cards_away INT,
    red_cards_home INT,
    red_cards_away INT,
    FOREIGN KEY (match_id) REFERENCES Matches(id)
);


CREATE TABLE Player_Statistics (
    id INT PRIMARY KEY,
    player_id INT,
    goals INT,
    assists INT,
    minutes_played INT,
    yellow_cards INT,
    red_cards INT,
    FOREIGN KEY (player_id) REFERENCES Player(id)
);

Create Table Coach(
	id int primary key unique,
    nationality int,
    name varchar(50),
    age int,
	FOREIGN KEY (nationality) REFERENCES Nation(id)

);

Create Table Nation(
	id int primary key unique,
    name varchar(50),
    flag text null
);


alter table league
	add column season_id int,
	add FOREIGN KEY (season_id) REFERENCES Season(id);
    
alter table player
	add column age int,
	add column nationality int,
	add FOREIGN KEY (nationality) REFERENCES Nation(id);
    
alter table matches
	drop column home_team_goals,
	drop column away_team_goals;
    
alter table Squad 
	add column coach_id int,
    add foreign key (coach_id) References coach(id);

