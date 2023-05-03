Create database KNK_Football;

use knk_football;

Create Table Team (
	id int PRIMARY KEY not null unique auto_increment,
    name varchar(50),
    stadium varchar(50) 
);

CREATE TABLE Player(
    id INT PRIMARY KEY not null unique auto_increment,
    name TEXT,
    position TEXT
);

CREATE TABLE Squad (
    id INT PRIMARY KEY not null unique auto_increment,
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
    id INT PRIMARY KEY not null auto_increment,
    home_team_id INT,
    away_team_id INT,
    home_team_goals INT,
    away_team_goals INT,
    match_date TIMESTAMP,
    FOREIGN KEY (home_team_id) REFERENCES Team(id),
    FOREIGN KEY (away_team_id) REFERENCES Team(id)
);

CREATE TABLE League (
    id INT PRIMARY KEY not null unique auto_increment,
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
	id INT PRIMARY KEY not null unique auto_increment,
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
  id INT PRIMARY KEY not null unique auto_increment,
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
	id INT PRIMARY KEY not null unique auto_increment,
    name varchar(10),
    start date,
    end date
);

Create Table Goal(
	id INT PRIMARY KEY not null unique auto_increment,
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
	id INT PRIMARY KEY not null unique auto_increment,
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
    id INT PRIMARY KEY not null unique auto_increment,
    player_id INT,
    goals INT,
    assists INT,
    minutes_played INT,
    yellow_cards INT,
    red_cards INT,
    FOREIGN KEY (player_id) REFERENCES Player(id)
);

Create Table Coach(
	id INT PRIMARY KEY not null unique auto_increment,
    nationality int,
    name varchar(50),
    age int,
	FOREIGN KEY (nationality) REFERENCES Nation(id)

);

Create Table Nation(
	id INT PRIMARY KEY not null unique auto_increment,
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
    
alter table league
    drop foreign key league_ibfk_1,
    drop column season_id;
    
alter table Season
	add column league_id int,
    add foreign key (league_id) references League(id);
    
alter table League
	add column leaguelogo text;
    
alter table Team
	modify column year varchar(10);

alter table Team 
	add column logo text;

    INSERT INTO Nation (name, flag)
VALUES 
("Andorra", "ad.png"),
("United Arab Emirates", "ae.png"),
("Afghanistan", "af.png"),
("Antigua and Barbuda", "ag.png"),
("Anguilla", "ai.png"),
("Albania", "al.png"),
("Armenia", "am.png"),
("Angola", "ao.png"),
("Antarctica", "aq.png"),
("Argentina", "ar.png"),
("American Samoa", "as.png"),
("Austria", "at.png"),
("Australia", "au.png"),
("Aruba", "aw.png"),
("\u00c5land Islands", "ax.png"),
("Azerbaijan", "az.png"),
("Bosnia and Herzegovina", "ba.png"),
("Barbados", "bb.png"),
("Bangladesh", "bd.png"),
("Belgium", "be.png"),
("Burkina Faso", "bf.png"),
("Bulgaria", "bg.png"),
("Bahrain", "bh.png"),
("Burundi", "bi.png"),
("Benin", "bj.png"),
("Saint Barthélemy", "bl.png"),
("Bermuda", "bm.png"),
("Brunei Darussalam", "bn.png"),
("Bolivia, Plurinational State of", "bo.png"),
("Caribbean Netherlands", "bq.png"),
("Brazil", "br.png"),
("Bahamas", "bs.png"),
("Bhutan", "bt.png"),
("Bouvet Island", "bv.png"),
("Botswana", "bw.png"),
("Belarus", "by.png"),
("Belize", "bz.png"),
("Canada", "ca.png"),
("Cocos (Keeling) Islands", "cc.png"),
("Congo, the Democratic Republic of the", "cd.png"),
("Central African Republic", "cf.png"),
("Republic of the Congo", "cg.png"),
("Switzerland", "ch.png"),
("Côte d'Ivoire", "ci.png"),
("Cook Islands", "ck.png"),
("Chile", "cl.png"),
("Cameroon", "cm.png"),
("China (People's Republic of China)", "cn.png"),
("Colombia", "co.png"),
("Costa Rica", "cr.png"),
("Cuba", "cu.png"),
("Cape Verde", "cv.png"),
("Curaçao", "cw.png"),
("Christmas Island", "cx.png"),
("Cyprus", "cy.png"),
("Czech Republic", "cz.png"),
("Germany", "de.png"),
("Djibouti", "dj.png"),
("Denmark", "dk.png"),
("Dominica", "dm.png"),
("Dominican Republic", "do.png"),
("Algeria", "dz.png"),
("Ecuador", "ec.png"),
("Estonia", "ee.png"),
("Egypt", "eg.png"),
("Western Sahara", "eh.png"),
("Eritrea", "er.png"),
("Spain", "es.png"),
("Ethiopia", "et.png"),
("Europe", "eu.png"),
("Finland", "fi.png"),
("Fiji", "fj.png"),
("Falkland Islands (Malvinas)", "fk.png"),
("Micronesia, Federated States of", "fm.png"),
("Faroe Islands", "fo.png"),
("France", "fr.png"),
("Gabon", "ga.png"),
("England", "gb-eng.png"),
("Northern Ireland", "gb-nir.png"),
("Scotland", "gb-sct.png"),
("Wales", "gb-wls.png"),
('United Kingdom', 'gb.png'),
('Grenada', 'gd.png'),
('Georgia', 'ge.png'),
('French Guiana', 'gf.png'),
('Guernsey', 'gg.png'),
('Ghana', 'gh.png'),
('Gibraltar', 'gi.png'),
('Greenland', 'gl.png'),
('Gambia', 'gm.png'),
('Guinea', 'gn.png'),
('Guadeloupe', 'gp.png'),
('Equatorial Guinea', 'gq.png'),
('Greece', 'gr.png'),
('South Georgia and the South Sandwich Islands', 'gs.png'),
('Guatemala', 'gt.png'),
('Guam', 'gu.png'),
('Guinea-Bissau', 'gw.png'),
('Guyana', 'gy.png'),
('Hong Kong', 'hk.png'),
('Heard Island and McDonald Islands', 'hm.png'),
('Honduras', 'hn.png'),
('Croatia', 'hr.png'),
('Haiti', 'ht.png'),
('Hungary', 'hu.png'),
('Indonesia', 'id.png'),
('Ireland', 'ie.png'),
('Israel', 'il.png'),
('Isle of Man', 'im.png'),
('India', 'in.png'),
('British Indian Ocean Territory', 'io.png'),
('Iraq', 'iq.png'),
('Iran, Islamic Republic of', 'ir.png'),
('Iceland', 'is.png'),
('Italy', 'it.png'),
('Jersey', 'je.png'),
('Jamaica', 'jm.png'),
('Jordan', 'jo.png'),
('Japan', 'jp.png'),
('Kenya', 'ke.png'),
('Kyrgyzstan', 'kg.png'),
('Cambodia', 'kh.png'),
('Kiribati', 'ki.png'),
('Comoros', 'km.png'),
('Saint Kitts and Nevis', 'kn.png'),
('Korea, Democratic People''s Republic of', 'kp.png'),
('Korea, Republic of', 'kr.png'),
('Kuwait', 'kw.png'),
('Cayman Islands', 'ky.png'),
('Kazakhstan', 'kz.png'),
('Laos (Lao People''s Democratic Republic)', 'la.png'),
('Lebanon', 'lb.png'),
('Saint Lucia', 'lc.png'),
('Liechtenstein', 'li.png'),
('Sri Lanka', 'lk.png'),
('Liberia', 'lr.png'),
('Lesotho', 'ls.png'),
('Lithuania', 'lt.png'),
('Luxembourg', 'lu.png'),
('Latvia', 'lv.png'),
('Libya', 'ly.png'),
('Morocco', 'ma.png'),
('Monaco', 'mc.png'),
('Moldova, Republic of', 'md.png'),
('Montenegro', 'me.png'),
('Saint Martin', 'mf.png'),
('Madagascar', 'mg.png'),
('Marshall Islands', 'mh.png'),
('North Macedonia', 'mk.png'),
('Mali', 'ml.png'),
('Myanmar', 'mm.png'),
('Mongolia', 'mn.png'),
('Macao', 'mo.png'),
('Northern Mariana Islands', 'mp.png'),
('Martinique', 'mq.png'),
('Mauritania', 'mr.png'),
('Montserrat', 'ms.png'),
('Malta', 'mt.png'),
('Mauritius', 'mu.png'),
('Maldives', 'mv.png'),
('Malawi', 'mw.png'),
    ('Mexico', 'mx.png'),
    ('Malaysia', 'my.png'),
    ('Mozambique', 'mz.png'),
    ('Namibia', 'na.png'),
    ('New Caledonia', 'nc.png'),
    ('Niger', 'ne.png'),
    ('Norfolk Island', 'nf.png'),
    ('Nigeria', 'ng.png'),
    ('Nicaragua', 'ni.png'),
    ('Netherlands', 'nl.png'),
    ('Norway', 'no.png'),
    ('Nepal', 'np.png'),
    ('Nauru', 'nr.png'),
    ('Niue', 'nu.png'),
    ('New Zealand', 'nz.png'),
    ('Oman', 'om.png'),
    ('Panama', 'pa.png'),
    ('Peru', 'pe.png'),
    ('French Polynesia', 'pf.png'),
    ('Papua New Guinea', 'pg.png'),
    ('Philippines', 'ph.png'),
    ('Pakistan', 'pk.png'),
    ('Poland', 'pl.png'),
    ('Saint Pierre and Miquelon', 'pm.png'),
    ('Pitcairn', 'pn.png'),
    ('Puerto Rico', 'pr.png'),
    ('Palestine', 'ps.png'),
    ('Portugal', 'pt.png'),
    ('Palau', 'pw.png'),
    ('Paraguay', 'py.png'),
    ('Qatar', 'qa.png'),
    ('Réunion', 're.png'),
    ('Romania', 'ro.png'),
    ('Serbia', 'rs.png'),
    ('Russian Federation', 'ru.png'),
    ('Rwanda', 'rw.png'),
    ('Saudi Arabia', 'sa.png'),
    ('Solomon Islands', 'sb.png'),
    ('Seychelles', 'sc.png'),
    ('Sudan', 'sd.png'),
    ('Sweden', 'se.png'),
    ('Singapore', 'sg.png'),
    ('Saint Helena, Ascension and Tristan da Cunha', 'sh.png'),
    ('Slovenia', 'si.png'),
    ('Svalbard and Jan Mayen Islands', 'sj.png'),
    ('Slovakia', 'sk.png'),
    ('Sierra Leone', 'sl.png'),
    ('San Marino', 'sm.png'),
    ('Senegal', 'sn.png'),
    ('Somalia', 'so.png'),
    ('Suriname', 'sr.png'),
    ('South Sudan', 'ss.png'),
    ('Sao Tome and Principe', 'st.png'),
    ('El Salvador', 'sv.png'),
    ('Sint Maarten (Dutch part)', 'sx.png'),
    ('Syrian Arab Republic', 'sy.png'),
    ('Swaziland', 'sz.png'),
    ('Turks and Caicos Islands', 'tc.png'),
    ('Chad', 'td.png'),
    ('French Southern Territories', 'tf.png'),
    ('Togo', 'tg.png'),
    ('Thailand', 'th.png'),
    ('Tajikistan', 'tj.png'),
    ('Tokelau', 'tk.png'),
    ('Timor-Leste', 'tl.png'),
    ('Turkmenistan', 'tm.png'),
    ('Tunisia', 'tn.png'),
    ('Tonga', 'to.png'),
    ('Turkey', 'tr.png'),
    ('Trinidad and Tobago', 'tt.png'),
('Tuvalu', 'tv.png'),
('Taiwan (Republic of China)', 'tw.png'),
('Tanzania, United Republic of', 'tz.png'),
('Ukraine', 'ua.png'),
('Uganda', 'ug.png'),
('US Minor Outlying Islands', 'um.png'),
('United States', 'us.png'),
('Uruguay', 'uy.png'),
('Uzbekistan', 'uz.png'),
('Holy See (Vatican City State)', 'va.png'),
('Saint Vincent and the Grenadines', 'vc.png'),
('Venezuela, Bolivarian Republic of', 've.png'),
('Virgin Islands, British', 'vg.png'),
('Virgin Islands, U.S.', 'vi.png'),
('Vietnam', 'vn.png'),
('Vanuatu', 'vu.png'),
('Wallis and Futuna Islands', 'wf.png'),
('Samoa', 'ws.png'),
('Kosovo', 'xk.png'),
('Yemen', 'ye.png'),
('Mayotte', 'yt.png'),
('South Africa', 'za.png'),
('Zambia', 'zm.png'),
('Zimbabwe', 'zw.png');


Select t.id, t.name ,t.stadium ,t.year, t.logo, l.name from Team t
inner join league_teams lt on lt.team_id = t.id
inner join league l on l.id = lt.league_id where l.id = 1;

/*delete from team where id =3;
delete from squad where id =2;
delete from league_teams where team_id =3;*/

alter table player 
	rename column age to birthday;

alter table player
	modify column birthday date;

alter table coach 
	rename column age to birthday;

alter table coach
	modify column birthday date;
    
alter table player 
	add column image text;
    
    
Select p.id, p.name, p.birthday, l.name, n.name , p.position , t.name From player p 
                Inner join squad_players sp on sp.player_id = p.id 
                Inner join squad s on sp.squad_id = s.id 
                Inner join team t on t.id = s.team_id
                Inner join nation n on n.id = p.nationality
				Inner join league_teams lt on lt.team_id = t.id
                Inner join league l on l.id = lt.league_id;
                
Select l.id, l.name , count(lt.team_id) from league l
inner join league_teams lt on lt.league_id = l.id
group by l.id;


SELECT l.id, l.name, COUNT(t.id) AS team_count
FROM League l
LEFT JOIN League_Teams lt ON l.id = lt.league_id
LEFT JOIN Team t ON lt.team_id = t.id
GROUP BY l.id, l.name;