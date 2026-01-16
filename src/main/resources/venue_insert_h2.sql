-- H2 SQL insert script for Venue (Top 3 soccer venues per country)
INSERT INTO venue (vn_id, vn_name, vn_display_name, vn_city, country_id, vn_capacity, vn_address, vn_image, vn_opened, vn_surface, vn_active, vn_created_at, vn_created_by)
VALUES
  -- Mexico (country_id: 1)
  (1, 'Estadio Azteca', 'Estadio Azteca', 'Mexico City', 1, 87523, 'Calzada de Tlalpan 3465, Santa Ursula Coapa, Mexico City', 'https://upload.wikimedia.org/wikipedia/commons/e/e9/Estadio_Azteca_2015.jpg', '1966-05-29', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (2, 'Estadio BBVA', 'Estadio BBVA', 'Monterrey', 1, 53500, 'Av. Pablo Livas 2011, Guadalupe, Nuevo Leon', 'https://upload.wikimedia.org/wikipedia/commons/a/a1/Estadio_BBVA_Bancomer.jpg', '2015-08-02', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (3, 'Estadio Jalisco', 'Estadio Jalisco', 'Guadalajara', 1, 55023, 'Calle 7 Colinas 1772, Independencia, Guadalajara, Jalisco', 'https://upload.wikimedia.org/wikipedia/commons/8/8a/Estadio_Jalisco.jpg', '1960-02-05', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Japan (country_id: 2)
  (4, 'Japan National Stadium', 'Japan National Stadium', 'Tokyo', 2, 68000, '10-1 Kasumigaokamachi, Shinjuku, Tokyo', 'https://upload.wikimedia.org/wikipedia/commons/d/d0/Japan_National_Stadium_2020.jpg', '2019-11-30', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (5, 'Saitama Stadium 2002', 'Saitama Stadium', 'Saitama', 2, 63700, '500 Misono, Midori-ku, Saitama', 'https://upload.wikimedia.org/wikipedia/commons/5/5d/Saitama_Stadium_2002.jpg', '2001-10-06', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (6, 'Nissan Stadium', 'Nissan Stadium', 'Yokohama', 2, 72327, '3300 Kozukue-cho, Kohoku-ku, Yokohama', 'https://upload.wikimedia.org/wikipedia/commons/7/7f/Nissan_Stadium.jpg', '1998-03-01', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Brazil (country_id: 3)
  (7, 'Maracana Stadium', 'Maracana', 'Rio de Janeiro', 3, 78838, 'Av. Pres. Castelo Branco, Maracana, Rio de Janeiro', 'https://upload.wikimedia.org/wikipedia/commons/9/9f/Maracana_Stadium.jpg', '1950-06-16', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (8, 'Allianz Parque', 'Allianz Parque', 'Sao Paulo', 3, 43713, 'Rua Palestra Italia 200, Perdizes, Sao Paulo', 'https://upload.wikimedia.org/wikipedia/commons/3/3d/Allianz_Parque.jpg', '2014-11-19', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (9, 'Neo Quimica Arena', 'Neo Quimica Arena', 'Sao Paulo', 3, 49205, 'Av. Miguel Ignacio Curi 111, Itaquera, Sao Paulo', 'https://upload.wikimedia.org/wikipedia/commons/e/e5/Arena_Corinthians.jpg', '2014-05-10', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Germany (country_id: 4)
  (10, 'Signal Iduna Park', 'Signal Iduna Park', 'Dortmund', 4, 81365, 'Strobelallee 50, 44139 Dortmund', 'https://upload.wikimedia.org/wikipedia/commons/4/4c/Signal_Iduna_Park.jpg', '1974-04-02', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (11, 'Allianz Arena', 'Allianz Arena', 'Munich', 4, 75024, 'Werner-Heisenberg-Allee 25, 80939 Munich', 'https://upload.wikimedia.org/wikipedia/commons/a/a8/Allianz_Arena.jpg', '2005-05-30', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (12, 'Olympiastadion Berlin', 'Olympiastadion Berlin', 'Berlin', 4, 74475, 'Olympischer Platz 3, 14053 Berlin', 'https://upload.wikimedia.org/wikipedia/commons/4/4e/Olympiastadion_Berlin.jpg', '1936-08-01', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- France (country_id: 5)
  (13, 'Stade de France', 'Stade de France', 'Saint-Denis', 5, 80698, '93216 Saint-Denis, Paris', 'https://upload.wikimedia.org/wikipedia/commons/6/6e/Stade_de_France.jpg', '1998-01-28', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (14, 'Parc des Princes', 'Parc des Princes', 'Paris', 5, 47929, '24 Rue du Commandant Guilbaud, 75016 Paris', 'https://upload.wikimedia.org/wikipedia/commons/f/f6/Parc_des_Princes.jpg', '1972-06-04', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (15, 'Stade Velodrome', 'Stade Velodrome', 'Marseille', 5, 67394, '3 Boulevard Michelet, 13008 Marseille', 'https://upload.wikimedia.org/wikipedia/commons/d/d0/Stade_Velodrome.jpg', '1937-06-13', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Spain (country_id: 6)
  (16, 'Santiago Bernabeu', 'Santiago Bernabeu', 'Madrid', 6, 81044, 'Av. de Concha Espina 1, 28036 Madrid', 'https://upload.wikimedia.org/wikipedia/commons/b/b4/Santiago_Bernabeu.jpg', '1947-12-14', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (17, 'Spotify Camp Nou', 'Camp Nou', 'Barcelona', 6, 99354, 'C. d''Aristides Maillol, 12, 08028 Barcelona', 'https://upload.wikimedia.org/wikipedia/commons/a/a9/Camp_Nou.jpg', '1957-09-24', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (18, 'Civitas Metropolitano', 'Civitas Metropolitano', 'Madrid', 6, 70460, 'Av. de Luis Aragones 4, 28022 Madrid', 'https://upload.wikimedia.org/wikipedia/commons/5/5d/Wanda_Metropolitano.jpg', '2017-09-16', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- England (country_id: 7)
  (19, 'Wembley Stadium', 'Wembley', 'London', 7, 90000, 'London HA9 0WS, United Kingdom', 'https://upload.wikimedia.org/wikipedia/commons/e/e0/Wembley_Stadium.jpg', '2007-03-09', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (20, 'Old Trafford', 'Old Trafford', 'Manchester', 7, 74310, 'Sir Matt Busby Way, Manchester M16 0RA', 'https://upload.wikimedia.org/wikipedia/commons/a/a8/Old_Trafford.jpg', '1910-02-19', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (21, 'Tottenham Hotspur Stadium', 'Tottenham Hotspur Stadium', 'London', 7, 62850, '782 High Road, London N17 0BX', 'https://upload.wikimedia.org/wikipedia/commons/d/d4/Tottenham_Hotspur_Stadium.jpg', '2019-04-03', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Italy (country_id: 8)
  (22, 'San Siro', 'San Siro', 'Milan', 8, 75923, 'Piazzale Angelo Moratti, 20151 Milan', 'https://upload.wikimedia.org/wikipedia/commons/7/76/San_Siro.jpg', '1926-09-19', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (23, 'Stadio Olimpico', 'Stadio Olimpico', 'Rome', 8, 70634, 'Viale dei Gladiatori, 00135 Rome', 'https://upload.wikimedia.org/wikipedia/commons/e/e5/Stadio_Olimpico_Rome.jpg', '1953-05-17', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (24, 'Allianz Stadium', 'Allianz Stadium', 'Turin', 8, 41507, 'Corso Gaetano Scirea 50, 10151 Turin', 'https://upload.wikimedia.org/wikipedia/commons/1/1f/Juventus_Stadium.jpg', '2011-09-08', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- United States (country_id: 9)
  (25, 'MetLife Stadium', 'MetLife Stadium', 'East Rutherford', 9, 82500, '1 MetLife Stadium Dr, East Rutherford, NJ 07073', 'https://upload.wikimedia.org/wikipedia/commons/a/a1/MetLife_Stadium.jpg', '2010-04-10', 'Artificial Turf', true, CURRENT_TIMESTAMP, 'admin'),
  (26, 'AT&T Stadium', 'AT&T Stadium', 'Arlington', 9, 80000, '1 AT&T Way, Arlington, TX 76011', 'https://upload.wikimedia.org/wikipedia/commons/2/2b/ATT_Stadium.jpg', '2009-05-27', 'Artificial Turf', true, CURRENT_TIMESTAMP, 'admin'),
  (27, 'Rose Bowl', 'Rose Bowl', 'Pasadena', 9, 90888, '1001 Rose Bowl Dr, Pasadena, CA 91103', 'https://upload.wikimedia.org/wikipedia/commons/5/5e/Rose_Bowl.jpg', '1922-10-28', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Netherlands (country_id: 10)
  (28, 'Johan Cruyff Arena', 'Johan Cruyff Arena', 'Amsterdam', 10, 55500, 'ArenA Boulevard 1, 1101 AX Amsterdam', 'https://upload.wikimedia.org/wikipedia/commons/6/6e/Johan_Cruyff_Arena.jpg', '1996-08-14', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (29, 'De Kuip', 'De Kuip', 'Rotterdam', 10, 51117, 'Van Zandvlietplein 1, 3077 AA Rotterdam', 'https://upload.wikimedia.org/wikipedia/commons/5/52/De_Kuip.jpg', '1937-03-27', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (30, 'Philips Stadion', 'Philips Stadion', 'Eindhoven', 10, 35000, 'Frederiklaan 10A, 5616 NH Eindhoven', 'https://upload.wikimedia.org/wikipedia/commons/8/88/Philips_Stadion.jpg', '1913-09-01', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Australia (country_id: 11)
  (31, 'Melbourne Cricket Ground', 'Melbourne Cricket Ground', 'Melbourne', 11, 100024, 'Brunton Ave, Richmond VIC 3002', 'https://upload.wikimedia.org/wikipedia/commons/2/21/MCG.jpg', '1853-09-30', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (32, 'Stadium Australia', 'Stadium Australia', 'Sydney', 11, 83500, 'Edwin Flack Ave, Sydney Olympic Park NSW 2127', 'https://upload.wikimedia.org/wikipedia/commons/4/4e/Stadium_Australia.jpg', '1999-03-06', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (33, 'AAMI Park', 'AAMI Park', 'Melbourne', 11, 30050, 'Olympic Blvd, Melbourne VIC 3004', 'https://upload.wikimedia.org/wikipedia/commons/8/80/AAMI_Park.jpg', '2010-05-07', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Canada (country_id: 12)
  (34, 'BC Place', 'BC Place', 'Vancouver', 12, 54500, '777 Pacific Blvd, Vancouver, BC V6B 4Y8', 'https://upload.wikimedia.org/wikipedia/commons/3/32/BC_Place.jpg', '1983-06-19', 'Artificial Turf', true, CURRENT_TIMESTAMP, 'admin'),
  (35, 'BMO Field', 'BMO Field', 'Toronto', 12, 30000, '170 Princes Blvd, Toronto, ON M6K 3C3', 'https://upload.wikimedia.org/wikipedia/commons/6/65/BMO_Field.jpg', '2007-04-28', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (36, 'Saputo Stadium', 'Saputo Stadium', 'Montreal', 12, 19619, '4750 Sherbrooke St E, Montreal, QC H1V 3S8', 'https://upload.wikimedia.org/wikipedia/commons/2/25/Saputo_Stadium.jpg', '2008-05-24', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Uruguay (country_id: 13)
  (37, 'Estadio Centenario', 'Estadio Centenario', 'Montevideo', 13, 60235, 'Av. Dr. Americo Ricaldoni s/n, Montevideo', 'https://upload.wikimedia.org/wikipedia/commons/4/46/Estadio_Centenario.jpg', '1930-07-18', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (38, 'Estadio Campeon del Siglo', 'Estadio Campeon del Siglo', 'Montevideo', 13, 40000, 'Camino Carrasco, Montevideo', 'https://upload.wikimedia.org/wikipedia/commons/d/d1/Estadio_Campeon_del_Siglo.jpg', '2016-03-22', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (39, 'Estadio Gran Parque Central', 'Gran Parque Central', 'Montevideo', 13, 34000, 'Av. 8 de Octubre 3181, Montevideo', 'https://upload.wikimedia.org/wikipedia/commons/e/e8/Gran_Parque_Central.jpg', '1900-05-25', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Chile (country_id: 14)
  (40, 'Estadio Nacional Julio Martinez Pradanos', 'Estadio Nacional', 'Santiago', 14, 48665, 'Av. Grecia 2001, Nunoa, Santiago', 'https://upload.wikimedia.org/wikipedia/commons/a/a5/Estadio_Nacional_Chile.jpg', '1938-12-03', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (41, 'Estadio Monumental David Arellano', 'Estadio Monumental', 'Santiago', 14, 47347, 'Av. Marathon 5300, Macul, Santiago', 'https://upload.wikimedia.org/wikipedia/commons/4/44/Estadio_Monumental_Colo-Colo.jpg', '1989-04-30', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (42, 'Estadio San Carlos de Apoquindo', 'San Carlos de Apoquindo', 'Santiago', 14, 14000, 'Camino Las Flores 13000, Las Condes, Santiago', 'https://upload.wikimedia.org/wikipedia/commons/d/df/San_Carlos_de_Apoquindo.jpg', '1988-11-08', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Colombia (country_id: 15)
  (43, 'Estadio Metropolitano Roberto Melendez', 'Estadio Metropolitano', 'Barranquilla', 15, 46788, 'Via 40 79A-65, Barranquilla', 'https://upload.wikimedia.org/wikipedia/commons/9/9e/Estadio_Metropolitano_Barranquilla.jpg', '1986-05-11', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (44, 'Estadio El Campin', 'El Campin', 'Bogota', 15, 36343, 'Carrera 30 57-60, Bogota', 'https://upload.wikimedia.org/wikipedia/commons/2/22/Estadio_El_Campin.jpg', '1938-08-10', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (45, 'Estadio Atanasio Girardot', 'Atanasio Girardot', 'Medellin', 15, 40943, 'Carrera 74 48-139, Medellin', 'https://upload.wikimedia.org/wikipedia/commons/7/71/Estadio_Atanasio_Girardot.jpg', '1953-03-19', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Peru (country_id: 16)
  (46, 'Estadio Nacional del Peru', 'Estadio Nacional', 'Lima', 16, 50000, 'Calle Jose Diaz s/n, Lima', 'https://upload.wikimedia.org/wikipedia/commons/4/43/Estadio_Nacional_Peru.jpg', '1952-10-27', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (47, 'Estadio Monumental U', 'Estadio Monumental', 'Lima', 16, 80093, 'Javier Prado Este 7000, Ate, Lima', 'https://upload.wikimedia.org/wikipedia/commons/6/68/Estadio_Monumental_Lima.jpg', '2000-07-02', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (48, 'Estadio Alejandro Villanueva', 'Estadio Alejandro Villanueva', 'Lima', 16, 35000, 'Jr. Mariscal Miller 411, La Victoria, Lima', 'https://upload.wikimedia.org/wikipedia/commons/a/a8/Estadio_Alianza_Lima.jpg', '1974-12-27', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Venezuela (country_id: 17)
  (49, 'Estadio Olimpico de la UCV', 'Estadio Olimpico UCV', 'Caracas', 17, 30000, 'Ciudad Universitaria de Caracas', 'https://upload.wikimedia.org/wikipedia/commons/b/bc/Estadio_Olimpico_UCV.jpg', '1951-12-01', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (50, 'Estadio Metropolitano de Merida', 'Estadio Metropolitano', 'Merida', 17, 42000, 'Av. Las Americas, Merida', 'https://upload.wikimedia.org/wikipedia/commons/2/29/Estadio_Metropolitano_Merida.jpg', '2007-06-15', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (51, 'Estadio Jose Antonio Anzoategui', 'Estadio Jose Antonio Anzoategui', 'Puerto La Cruz', 17, 36000, 'Av. Intercomunal, Puerto La Cruz', 'https://upload.wikimedia.org/wikipedia/commons/5/5e/Estadio_Anzoategui.jpg', '2007-03-20', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- South Africa (country_id: 18)
  (52, 'FNB Stadium', 'FNB Stadium', 'Johannesburg', 18, 94736, 'Soccer City Ave, Nasrec, Johannesburg', 'https://upload.wikimedia.org/wikipedia/commons/4/48/Soccer_City.jpg', '1989-04-12', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (53, 'Cape Town Stadium', 'Cape Town Stadium', 'Cape Town', 18, 55000, 'Fritz Sonnenberg Road, Green Point, Cape Town', 'https://upload.wikimedia.org/wikipedia/commons/e/e3/Cape_Town_Stadium.jpg', '2009-12-14', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (54, 'Moses Mabhida Stadium', 'Moses Mabhida Stadium', 'Durban', 18, 54000, '44 Isaiah Ntshangase Rd, Durban', 'https://upload.wikimedia.org/wikipedia/commons/d/d0/Moses_Mabhida_Stadium.jpg', '2009-11-28', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- New Zealand (country_id: 19)
  (55, 'Eden Park', 'Eden Park', 'Auckland', 19, 50000, 'Reimers Ave, Kingsland, Auckland', 'https://upload.wikimedia.org/wikipedia/commons/5/5e/Eden_Park.jpg', '1900-12-01', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (56, 'Wellington Regional Stadium', 'Sky Stadium', 'Wellington', 19, 34500, '105 Waterloo Quay, Wellington', 'https://upload.wikimedia.org/wikipedia/commons/a/a6/Westpac_Stadium.jpg', '2000-01-03', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (57, 'Forsyth Barr Stadium', 'Forsyth Barr Stadium', 'Dunedin', 19, 30748, '130 Anzac Ave, Dunedin', 'https://upload.wikimedia.org/wikipedia/commons/9/96/Forsyth_Barr_Stadium.jpg', '2011-08-05', 'Artificial Turf', true, CURRENT_TIMESTAMP, 'admin'),

  -- Ireland (country_id: 20)
  (58, 'Aviva Stadium', 'Aviva Stadium', 'Dublin', 20, 51700, '62 Lansdowne Rd, Dublin 4', 'https://upload.wikimedia.org/wikipedia/commons/b/b5/Aviva_Stadium.jpg', '2010-05-14', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (59, 'Croke Park', 'Croke Park', 'Dublin', 20, 82300, 'St Josephs Ave, Croke Park, Dublin 3', 'https://upload.wikimedia.org/wikipedia/commons/b/b0/Croke_Park.jpg', '1884-01-01', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (60, 'Tallaght Stadium', 'Tallaght Stadium', 'Dublin', 20, 10500, 'Whitestown Way, Tallaght, Dublin 24', 'https://upload.wikimedia.org/wikipedia/commons/7/71/Tallaght_Stadium.jpg', '2009-07-20', 'Artificial Turf', true, CURRENT_TIMESTAMP, 'admin'),

  -- Sweden (country_id: 21)
  (61, 'Friends Arena', 'Friends Arena', 'Solna', 21, 50000, 'Rasundavagen 100, 169 56 Solna', 'https://upload.wikimedia.org/wikipedia/commons/e/e5/Friends_Arena.jpg', '2012-10-27', 'Artificial Turf', true, CURRENT_TIMESTAMP, 'admin'),
  (62, 'Ullevi', 'Ullevi', 'Gothenburg', 21, 43200, 'Levgrensvägen 16, 412 51 Gothenburg', 'https://upload.wikimedia.org/wikipedia/commons/5/55/Ullevi.jpg', '1958-05-29', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (63, 'Eleda Stadion', 'Eleda Stadion', 'Malmo', 21, 24000, 'Stadiongatan 65, 217 62 Malmo', 'https://upload.wikimedia.org/wikipedia/commons/3/30/Swedbank_Stadion.jpg', '2009-04-13', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Nigeria (country_id: 22)
  (64, 'Moshood Abiola National Stadium', 'Moshood Abiola Stadium', 'Abuja', 22, 60000, 'Area 10, Garki, Abuja', 'https://upload.wikimedia.org/wikipedia/commons/7/72/Abuja_Stadium.jpg', '2003-10-01', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (65, 'Teslim Balogun Stadium', 'Teslim Balogun Stadium', 'Lagos', 22, 30000, 'Surulere, Lagos', 'https://upload.wikimedia.org/wikipedia/commons/3/39/Teslim_Balogun_Stadium.jpg', '1972-01-01', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (66, 'Godswill Akpabio International Stadium', 'Godswill Akpabio Stadium', 'Uyo', 22, 30000, 'Uyo, Akwa Ibom State', 'https://upload.wikimedia.org/wikipedia/commons/a/a7/Godswill_Akpabio_Stadium.jpg', '2014-11-15', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Egypt (country_id: 23)
  (67, 'Cairo International Stadium', 'Cairo International Stadium', 'Cairo', 23, 75000, 'Nasr City, Cairo', 'https://upload.wikimedia.org/wikipedia/commons/f/f4/Cairo_International_Stadium.jpg', '1960-07-23', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (68, 'Borg El Arab Stadium', 'Borg El Arab Stadium', 'Alexandria', 23, 86000, 'Borg El Arab, Alexandria', 'https://upload.wikimedia.org/wikipedia/commons/8/8c/Borg_El_Arab_Stadium.jpg', '2007-02-15', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (69, 'Al Ahly WE Al Salam Stadium', 'Al Ahly Stadium', 'Cairo', 23, 30000, 'New Administrative Capital, Cairo', 'https://upload.wikimedia.org/wikipedia/commons/3/35/Al_Ahly_Stadium.jpg', '2021-06-24', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Saudi Arabia (country_id: 24)
  (70, 'King Fahd International Stadium', 'King Fahd Stadium', 'Riyadh', 24, 68752, 'Al Faisaliyah District, Riyadh', 'https://upload.wikimedia.org/wikipedia/commons/0/00/King_Fahd_Stadium.jpg', '1987-11-29', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (71, 'King Abdullah Sports City Stadium', 'King Abdullah Sports City', 'Jeddah', 24, 62345, 'Al Naseem District, Jeddah', 'https://upload.wikimedia.org/wikipedia/commons/7/79/King_Abdullah_Sports_City.jpg', '2014-05-01', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (72, 'Prince Faisal bin Fahd Stadium', 'Prince Faisal Stadium', 'Riyadh', 24, 22500, 'Al-Malaz District, Riyadh', 'https://upload.wikimedia.org/wikipedia/commons/c/c2/Prince_Faisal_Stadium.jpg', '1971-01-01', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- United Arab Emirates (country_id: 25)
  (73, 'Hazza Bin Zayed Stadium', 'Hazza Bin Zayed Stadium', 'Al Ain', 25, 25000, 'Al Ain, Abu Dhabi', 'https://upload.wikimedia.org/wikipedia/commons/4/42/Hazza_Bin_Zayed_Stadium.jpg', '2014-01-21', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (74, 'Mohammed Bin Zayed Stadium', 'Mohammed Bin Zayed Stadium', 'Abu Dhabi', 25, 42056, 'Mohammed Bin Zayed City, Abu Dhabi', 'https://upload.wikimedia.org/wikipedia/commons/9/9f/MBZ_Stadium.jpg', '2008-05-08', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (75, 'Al Maktoum Stadium', 'Al Maktoum Stadium', 'Dubai', 25, 15000, 'Al Nasr, Dubai', 'https://upload.wikimedia.org/wikipedia/commons/a/a5/Al_Maktoum_Stadium.jpg', '1996-01-01', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),

  -- Argentina (country_id: 26)
  (76, 'Estadio Mas Monumental', 'Estadio Monumental', 'Buenos Aires', 26, 84567, 'Av. Figueroa Alcorta 7597, Buenos Aires', 'https://upload.wikimedia.org/wikipedia/commons/3/3f/Estadio_Monumental.jpg', '1938-05-26', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (77, 'La Bombonera', 'La Bombonera', 'Buenos Aires', 26, 54000, 'Brandsen 805, La Boca, Buenos Aires', 'https://upload.wikimedia.org/wikipedia/commons/3/35/La_Bombonera.jpg', '1940-05-25', 'Grass', true, CURRENT_TIMESTAMP, 'admin'),
  (78, 'Estadio Mario Alberto Kempes', 'Estadio Kempes', 'Cordoba', 26, 57000, 'Av. del Libertador Simon Bolivar, Cordoba', 'https://upload.wikimedia.org/wikipedia/commons/b/b4/Estadio_Kempes.jpg', '1978-05-28', 'Grass', true, CURRENT_TIMESTAMP, 'admin');
