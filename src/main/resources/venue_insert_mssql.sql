-- MSSQL insert script for Venue (Top 3 soccer venues per country)
SET IDENTITY_INSERT venue ON;

INSERT INTO venue (vn_id, vn_name, vn_display_name, vn_city, country_id, vn_capacity, vn_address, vn_image, vn_opened, vn_surface, vn_active, vn_created_at, vn_created_by)
VALUES
  -- Mexico (country_id: 1)
  (1, N'Estadio Azteca', N'Estadio Azteca', N'Mexico City', 1, 87523, N'Calzada de Tlalpan 3465, Santa Ursula Coapa, Mexico City', N'https://upload.wikimedia.org/wikipedia/commons/e/e9/Estadio_Azteca_2015.jpg', '1966-05-29', N'Grass', 1, GETDATE(), N'admin'),
  (2, N'Estadio BBVA', N'Estadio BBVA', N'Monterrey', 1, 53500, N'Av. Pablo Livas 2011, Guadalupe, Nuevo Leon', N'https://upload.wikimedia.org/wikipedia/commons/a/a1/Estadio_BBVA_Bancomer.jpg', '2015-08-02', N'Grass', 1, GETDATE(), N'admin'),
  (3, N'Estadio Jalisco', N'Estadio Jalisco', N'Guadalajara', 1, 55023, N'Calle 7 Colinas 1772, Independencia, Guadalajara, Jalisco', N'https://upload.wikimedia.org/wikipedia/commons/8/8a/Estadio_Jalisco.jpg', '1960-02-05', N'Grass', 1, GETDATE(), N'admin'),

  -- Japan (country_id: 2)
  (4, N'Japan National Stadium', N'Japan National Stadium', N'Tokyo', 2, 68000, N'10-1 Kasumigaokamachi, Shinjuku, Tokyo', N'https://upload.wikimedia.org/wikipedia/commons/d/d0/Japan_National_Stadium_2020.jpg', '2019-11-30', N'Grass', 1, GETDATE(), N'admin'),
  (5, N'Saitama Stadium 2002', N'Saitama Stadium', N'Saitama', 2, 63700, N'500 Misono, Midori-ku, Saitama', N'https://upload.wikimedia.org/wikipedia/commons/5/5d/Saitama_Stadium_2002.jpg', '2001-10-06', N'Grass', 1, GETDATE(), N'admin'),
  (6, N'Nissan Stadium', N'Nissan Stadium', N'Yokohama', 2, 72327, N'3300 Kozukue-cho, Kohoku-ku, Yokohama', N'https://upload.wikimedia.org/wikipedia/commons/7/7f/Nissan_Stadium.jpg', '1998-03-01', N'Grass', 1, GETDATE(), N'admin'),

  -- Brazil (country_id: 3)
  (7, N'Maracana Stadium', N'Maracana', N'Rio de Janeiro', 3, 78838, N'Av. Pres. Castelo Branco, Maracana, Rio de Janeiro', N'https://upload.wikimedia.org/wikipedia/commons/9/9f/Maracana_Stadium.jpg', '1950-06-16', N'Grass', 1, GETDATE(), N'admin'),
  (8, N'Allianz Parque', N'Allianz Parque', N'Sao Paulo', 3, 43713, N'Rua Palestra Italia 200, Perdizes, Sao Paulo', N'https://upload.wikimedia.org/wikipedia/commons/3/3d/Allianz_Parque.jpg', '2014-11-19', N'Grass', 1, GETDATE(), N'admin'),
  (9, N'Neo Quimica Arena', N'Neo Quimica Arena', N'Sao Paulo', 3, 49205, N'Av. Miguel Ignacio Curi 111, Itaquera, Sao Paulo', N'https://upload.wikimedia.org/wikipedia/commons/e/e5/Arena_Corinthians.jpg', '2014-05-10', N'Grass', 1, GETDATE(), N'admin'),

  -- Germany (country_id: 4)
  (10, N'Signal Iduna Park', N'Signal Iduna Park', N'Dortmund', 4, 81365, N'Strobelallee 50, 44139 Dortmund', N'https://upload.wikimedia.org/wikipedia/commons/4/4c/Signal_Iduna_Park.jpg', '1974-04-02', N'Grass', 1, GETDATE(), N'admin'),
  (11, N'Allianz Arena', N'Allianz Arena', N'Munich', 4, 75024, N'Werner-Heisenberg-Allee 25, 80939 Munich', N'https://upload.wikimedia.org/wikipedia/commons/a/a8/Allianz_Arena.jpg', '2005-05-30', N'Grass', 1, GETDATE(), N'admin'),
  (12, N'Olympiastadion Berlin', N'Olympiastadion Berlin', N'Berlin', 4, 74475, N'Olympischer Platz 3, 14053 Berlin', N'https://upload.wikimedia.org/wikipedia/commons/4/4e/Olympiastadion_Berlin.jpg', '1936-08-01', N'Grass', 1, GETDATE(), N'admin'),

  -- France (country_id: 5)
  (13, N'Stade de France', N'Stade de France', N'Saint-Denis', 5, 80698, N'93216 Saint-Denis, Paris', N'https://upload.wikimedia.org/wikipedia/commons/6/6e/Stade_de_France.jpg', '1998-01-28', N'Grass', 1, GETDATE(), N'admin'),
  (14, N'Parc des Princes', N'Parc des Princes', N'Paris', 5, 47929, N'24 Rue du Commandant Guilbaud, 75016 Paris', N'https://upload.wikimedia.org/wikipedia/commons/f/f6/Parc_des_Princes.jpg', '1972-06-04', N'Grass', 1, GETDATE(), N'admin'),
  (15, N'Stade Velodrome', N'Stade Velodrome', N'Marseille', 5, 67394, N'3 Boulevard Michelet, 13008 Marseille', N'https://upload.wikimedia.org/wikipedia/commons/d/d0/Stade_Velodrome.jpg', '1937-06-13', N'Grass', 1, GETDATE(), N'admin'),

  -- Spain (country_id: 6)
  (16, N'Santiago Bernabeu', N'Santiago Bernabeu', N'Madrid', 6, 81044, N'Av. de Concha Espina 1, 28036 Madrid', N'https://upload.wikimedia.org/wikipedia/commons/b/b4/Santiago_Bernabeu.jpg', '1947-12-14', N'Grass', 1, GETDATE(), N'admin'),
  (17, N'Spotify Camp Nou', N'Camp Nou', N'Barcelona', 6, 99354, N'C. d''Aristides Maillol, 12, 08028 Barcelona', N'https://upload.wikimedia.org/wikipedia/commons/a/a9/Camp_Nou.jpg', '1957-09-24', N'Grass', 1, GETDATE(), N'admin'),
  (18, N'Civitas Metropolitano', N'Civitas Metropolitano', N'Madrid', 6, 70460, N'Av. de Luis Aragones 4, 28022 Madrid', N'https://upload.wikimedia.org/wikipedia/commons/5/5d/Wanda_Metropolitano.jpg', '2017-09-16', N'Grass', 1, GETDATE(), N'admin'),

  -- England (country_id: 7)
  (19, N'Wembley Stadium', N'Wembley', N'London', 7, 90000, N'London HA9 0WS, United Kingdom', N'https://upload.wikimedia.org/wikipedia/commons/e/e0/Wembley_Stadium.jpg', '2007-03-09', N'Grass', 1, GETDATE(), N'admin'),
  (20, N'Old Trafford', N'Old Trafford', N'Manchester', 7, 74310, N'Sir Matt Busby Way, Manchester M16 0RA', N'https://upload.wikimedia.org/wikipedia/commons/a/a8/Old_Trafford.jpg', '1910-02-19', N'Grass', 1, GETDATE(), N'admin'),
  (21, N'Tottenham Hotspur Stadium', N'Tottenham Hotspur Stadium', N'London', 7, 62850, N'782 High Road, London N17 0BX', N'https://upload.wikimedia.org/wikipedia/commons/d/d4/Tottenham_Hotspur_Stadium.jpg', '2019-04-03', N'Grass', 1, GETDATE(), N'admin'),

  -- Italy (country_id: 8)
  (22, N'San Siro', N'San Siro', N'Milan', 8, 75923, N'Piazzale Angelo Moratti, 20151 Milan', N'https://upload.wikimedia.org/wikipedia/commons/7/76/San_Siro.jpg', '1926-09-19', N'Grass', 1, GETDATE(), N'admin'),
  (23, N'Stadio Olimpico', N'Stadio Olimpico', N'Rome', 8, 70634, N'Viale dei Gladiatori, 00135 Rome', N'https://upload.wikimedia.org/wikipedia/commons/e/e5/Stadio_Olimpico_Rome.jpg', '1953-05-17', N'Grass', 1, GETDATE(), N'admin'),
  (24, N'Allianz Stadium', N'Allianz Stadium', N'Turin', 8, 41507, N'Corso Gaetano Scirea 50, 10151 Turin', N'https://upload.wikimedia.org/wikipedia/commons/1/1f/Juventus_Stadium.jpg', '2011-09-08', N'Grass', 1, GETDATE(), N'admin'),

  -- United States (country_id: 9)
  (25, N'MetLife Stadium', N'MetLife Stadium', N'East Rutherford', 9, 82500, N'1 MetLife Stadium Dr, East Rutherford, NJ 07073', N'https://upload.wikimedia.org/wikipedia/commons/a/a1/MetLife_Stadium.jpg', '2010-04-10', N'Artificial Turf', 1, GETDATE(), N'admin'),
  (26, N'AT&T Stadium', N'AT&T Stadium', N'Arlington', 9, 80000, N'1 AT&T Way, Arlington, TX 76011', N'https://upload.wikimedia.org/wikipedia/commons/2/2b/ATT_Stadium.jpg', '2009-05-27', N'Artificial Turf', 1, GETDATE(), N'admin'),
  (27, N'Rose Bowl', N'Rose Bowl', N'Pasadena', 9, 90888, N'1001 Rose Bowl Dr, Pasadena, CA 91103', N'https://upload.wikimedia.org/wikipedia/commons/5/5e/Rose_Bowl.jpg', '1922-10-28', N'Grass', 1, GETDATE(), N'admin'),

  -- Netherlands (country_id: 10)
  (28, N'Johan Cruyff Arena', N'Johan Cruyff Arena', N'Amsterdam', 10, 55500, N'ArenA Boulevard 1, 1101 AX Amsterdam', N'https://upload.wikimedia.org/wikipedia/commons/6/6e/Johan_Cruyff_Arena.jpg', '1996-08-14', N'Grass', 1, GETDATE(), N'admin'),
  (29, N'De Kuip', N'De Kuip', N'Rotterdam', 10, 51117, N'Van Zandvlietplein 1, 3077 AA Rotterdam', N'https://upload.wikimedia.org/wikipedia/commons/5/52/De_Kuip.jpg', '1937-03-27', N'Grass', 1, GETDATE(), N'admin'),
  (30, N'Philips Stadion', N'Philips Stadion', N'Eindhoven', 10, 35000, N'Frederiklaan 10A, 5616 NH Eindhoven', N'https://upload.wikimedia.org/wikipedia/commons/8/88/Philips_Stadion.jpg', '1913-09-01', N'Grass', 1, GETDATE(), N'admin'),

  -- Australia (country_id: 11)
  (31, N'Melbourne Cricket Ground', N'Melbourne Cricket Ground', N'Melbourne', 11, 100024, N'Brunton Ave, Richmond VIC 3002', N'https://upload.wikimedia.org/wikipedia/commons/2/21/MCG.jpg', '1853-09-30', N'Grass', 1, GETDATE(), N'admin'),
  (32, N'Stadium Australia', N'Stadium Australia', N'Sydney', 11, 83500, N'Edwin Flack Ave, Sydney Olympic Park NSW 2127', N'https://upload.wikimedia.org/wikipedia/commons/4/4e/Stadium_Australia.jpg', '1999-03-06', N'Grass', 1, GETDATE(), N'admin'),
  (33, N'AAMI Park', N'AAMI Park', N'Melbourne', 11, 30050, N'Olympic Blvd, Melbourne VIC 3004', N'https://upload.wikimedia.org/wikipedia/commons/8/80/AAMI_Park.jpg', '2010-05-07', N'Grass', 1, GETDATE(), N'admin'),

  -- Canada (country_id: 12)
  (34, N'BC Place', N'BC Place', N'Vancouver', 12, 54500, N'777 Pacific Blvd, Vancouver, BC V6B 4Y8', N'https://upload.wikimedia.org/wikipedia/commons/3/32/BC_Place.jpg', '1983-06-19', N'Artificial Turf', 1, GETDATE(), N'admin'),
  (35, N'BMO Field', N'BMO Field', N'Toronto', 12, 30000, N'170 Princes Blvd, Toronto, ON M6K 3C3', N'https://upload.wikimedia.org/wikipedia/commons/6/65/BMO_Field.jpg', '2007-04-28', N'Grass', 1, GETDATE(), N'admin'),
  (36, N'Saputo Stadium', N'Saputo Stadium', N'Montreal', 12, 19619, N'4750 Sherbrooke St E, Montreal, QC H1V 3S8', N'https://upload.wikimedia.org/wikipedia/commons/2/25/Saputo_Stadium.jpg', '2008-05-24', N'Grass', 1, GETDATE(), N'admin'),

  -- Uruguay (country_id: 13)
  (37, N'Estadio Centenario', N'Estadio Centenario', N'Montevideo', 13, 60235, N'Av. Dr. Americo Ricaldoni s/n, Montevideo', N'https://upload.wikimedia.org/wikipedia/commons/4/46/Estadio_Centenario.jpg', '1930-07-18', N'Grass', 1, GETDATE(), N'admin'),
  (38, N'Estadio Campeon del Siglo', N'Estadio Campeon del Siglo', N'Montevideo', 13, 40000, N'Camino Carrasco, Montevideo', N'https://upload.wikimedia.org/wikipedia/commons/d/d1/Estadio_Campeon_del_Siglo.jpg', '2016-03-22', N'Grass', 1, GETDATE(), N'admin'),
  (39, N'Estadio Gran Parque Central', N'Gran Parque Central', N'Montevideo', 13, 34000, N'Av. 8 de Octubre 3181, Montevideo', N'https://upload.wikimedia.org/wikipedia/commons/e/e8/Gran_Parque_Central.jpg', '1900-05-25', N'Grass', 1, GETDATE(), N'admin'),

  -- Chile (country_id: 14)
  (40, N'Estadio Nacional Julio Martinez Pradanos', N'Estadio Nacional', N'Santiago', 14, 48665, N'Av. Grecia 2001, Nunoa, Santiago', N'https://upload.wikimedia.org/wikipedia/commons/a/a5/Estadio_Nacional_Chile.jpg', '1938-12-03', N'Grass', 1, GETDATE(), N'admin'),
  (41, N'Estadio Monumental David Arellano', N'Estadio Monumental', N'Santiago', 14, 47347, N'Av. Marathon 5300, Macul, Santiago', N'https://upload.wikimedia.org/wikipedia/commons/4/44/Estadio_Monumental_Colo-Colo.jpg', '1989-04-30', N'Grass', 1, GETDATE(), N'admin'),
  (42, N'Estadio San Carlos de Apoquindo', N'San Carlos de Apoquindo', N'Santiago', 14, 14000, N'Camino Las Flores 13000, Las Condes, Santiago', N'https://upload.wikimedia.org/wikipedia/commons/d/df/San_Carlos_de_Apoquindo.jpg', '1988-11-08', N'Grass', 1, GETDATE(), N'admin'),

  -- Colombia (country_id: 15)
  (43, N'Estadio Metropolitano Roberto Melendez', N'Estadio Metropolitano', N'Barranquilla', 15, 46788, N'Via 40 79A-65, Barranquilla', N'https://upload.wikimedia.org/wikipedia/commons/9/9e/Estadio_Metropolitano_Barranquilla.jpg', '1986-05-11', N'Grass', 1, GETDATE(), N'admin'),
  (44, N'Estadio El Campin', N'El Campin', N'Bogota', 15, 36343, N'Carrera 30 57-60, Bogota', N'https://upload.wikimedia.org/wikipedia/commons/2/22/Estadio_El_Campin.jpg', '1938-08-10', N'Grass', 1, GETDATE(), N'admin'),
  (45, N'Estadio Atanasio Girardot', N'Atanasio Girardot', N'Medellin', 15, 40943, N'Carrera 74 48-139, Medellin', N'https://upload.wikimedia.org/wikipedia/commons/7/71/Estadio_Atanasio_Girardot.jpg', '1953-03-19', N'Grass', 1, GETDATE(), N'admin'),

  -- Peru (country_id: 16)
  (46, N'Estadio Nacional del Peru', N'Estadio Nacional', N'Lima', 16, 50000, N'Calle Jose Diaz s/n, Lima', N'https://upload.wikimedia.org/wikipedia/commons/4/43/Estadio_Nacional_Peru.jpg', '1952-10-27', N'Grass', 1, GETDATE(), N'admin'),
  (47, N'Estadio Monumental U', N'Estadio Monumental', N'Lima', 16, 80093, N'Javier Prado Este 7000, Ate, Lima', N'https://upload.wikimedia.org/wikipedia/commons/6/68/Estadio_Monumental_Lima.jpg', '2000-07-02', N'Grass', 1, GETDATE(), N'admin'),
  (48, N'Estadio Alejandro Villanueva', N'Estadio Alejandro Villanueva', N'Lima', 16, 35000, N'Jr. Mariscal Miller 411, La Victoria, Lima', N'https://upload.wikimedia.org/wikipedia/commons/a/a8/Estadio_Alianza_Lima.jpg', '1974-12-27', N'Grass', 1, GETDATE(), N'admin'),

  -- Venezuela (country_id: 17)
  (49, N'Estadio Olimpico de la UCV', N'Estadio Olimpico UCV', N'Caracas', 17, 30000, N'Ciudad Universitaria de Caracas', N'https://upload.wikimedia.org/wikipedia/commons/b/bc/Estadio_Olimpico_UCV.jpg', '1951-12-01', N'Grass', 1, GETDATE(), N'admin'),
  (50, N'Estadio Metropolitano de Merida', N'Estadio Metropolitano', N'Merida', 17, 42000, N'Av. Las Americas, Merida', N'https://upload.wikimedia.org/wikipedia/commons/2/29/Estadio_Metropolitano_Merida.jpg', '2007-06-15', N'Grass', 1, GETDATE(), N'admin'),
  (51, N'Estadio Jose Antonio Anzoategui', N'Estadio Jose Antonio Anzoategui', N'Puerto La Cruz', 17, 36000, N'Av. Intercomunal, Puerto La Cruz', N'https://upload.wikimedia.org/wikipedia/commons/5/5e/Estadio_Anzoategui.jpg', '2007-03-20', N'Grass', 1, GETDATE(), N'admin'),

  -- South Africa (country_id: 18)
  (52, N'FNB Stadium', N'FNB Stadium', N'Johannesburg', 18, 94736, N'Soccer City Ave, Nasrec, Johannesburg', N'https://upload.wikimedia.org/wikipedia/commons/4/48/Soccer_City.jpg', '1989-04-12', N'Grass', 1, GETDATE(), N'admin'),
  (53, N'Cape Town Stadium', N'Cape Town Stadium', N'Cape Town', 18, 55000, N'Fritz Sonnenberg Road, Green Point, Cape Town', N'https://upload.wikimedia.org/wikipedia/commons/e/e3/Cape_Town_Stadium.jpg', '2009-12-14', N'Grass', 1, GETDATE(), N'admin'),
  (54, N'Moses Mabhida Stadium', N'Moses Mabhida Stadium', N'Durban', 18, 54000, N'44 Isaiah Ntshangase Rd, Durban', N'https://upload.wikimedia.org/wikipedia/commons/d/d0/Moses_Mabhida_Stadium.jpg', '2009-11-28', N'Grass', 1, GETDATE(), N'admin'),

  -- New Zealand (country_id: 19)
  (55, N'Eden Park', N'Eden Park', N'Auckland', 19, 50000, N'Reimers Ave, Kingsland, Auckland', N'https://upload.wikimedia.org/wikipedia/commons/5/5e/Eden_Park.jpg', '1900-12-01', N'Grass', 1, GETDATE(), N'admin'),
  (56, N'Wellington Regional Stadium', N'Sky Stadium', N'Wellington', 19, 34500, N'105 Waterloo Quay, Wellington', N'https://upload.wikimedia.org/wikipedia/commons/a/a6/Westpac_Stadium.jpg', '2000-01-03', N'Grass', 1, GETDATE(), N'admin'),
  (57, N'Forsyth Barr Stadium', N'Forsyth Barr Stadium', N'Dunedin', 19, 30748, N'130 Anzac Ave, Dunedin', N'https://upload.wikimedia.org/wikipedia/commons/9/96/Forsyth_Barr_Stadium.jpg', '2011-08-05', N'Artificial Turf', 1, GETDATE(), N'admin'),

  -- Ireland (country_id: 20)
  (58, N'Aviva Stadium', N'Aviva Stadium', N'Dublin', 20, 51700, N'62 Lansdowne Rd, Dublin 4', N'https://upload.wikimedia.org/wikipedia/commons/b/b5/Aviva_Stadium.jpg', '2010-05-14', N'Grass', 1, GETDATE(), N'admin'),
  (59, N'Croke Park', N'Croke Park', N'Dublin', 20, 82300, N'St Josephs Ave, Croke Park, Dublin 3', N'https://upload.wikimedia.org/wikipedia/commons/b/b0/Croke_Park.jpg', '1884-01-01', N'Grass', 1, GETDATE(), N'admin'),
  (60, N'Tallaght Stadium', N'Tallaght Stadium', N'Dublin', 20, 10500, N'Whitestown Way, Tallaght, Dublin 24', N'https://upload.wikimedia.org/wikipedia/commons/7/71/Tallaght_Stadium.jpg', '2009-07-20', N'Artificial Turf', 1, GETDATE(), N'admin'),

  -- Sweden (country_id: 21)
  (61, N'Friends Arena', N'Friends Arena', N'Solna', 21, 50000, N'Rasundavagen 100, 169 56 Solna', N'https://upload.wikimedia.org/wikipedia/commons/e/e5/Friends_Arena.jpg', '2012-10-27', N'Artificial Turf', 1, GETDATE(), N'admin'),
  (62, N'Ullevi', N'Ullevi', N'Gothenburg', 21, 43200, N'Levgrensvagen 16, 412 51 Gothenburg', N'https://upload.wikimedia.org/wikipedia/commons/5/55/Ullevi.jpg', '1958-05-29', N'Grass', 1, GETDATE(), N'admin'),
  (63, N'Eleda Stadion', N'Eleda Stadion', N'Malmo', 21, 24000, N'Stadiongatan 65, 217 62 Malmo', N'https://upload.wikimedia.org/wikipedia/commons/3/30/Swedbank_Stadion.jpg', '2009-04-13', N'Grass', 1, GETDATE(), N'admin'),

  -- Nigeria (country_id: 22)
  (64, N'Moshood Abiola National Stadium', N'Moshood Abiola Stadium', N'Abuja', 22, 60000, N'Area 10, Garki, Abuja', N'https://upload.wikimedia.org/wikipedia/commons/7/72/Abuja_Stadium.jpg', '2003-10-01', N'Grass', 1, GETDATE(), N'admin'),
  (65, N'Teslim Balogun Stadium', N'Teslim Balogun Stadium', N'Lagos', 22, 30000, N'Surulere, Lagos', N'https://upload.wikimedia.org/wikipedia/commons/3/39/Teslim_Balogun_Stadium.jpg', '1972-01-01', N'Grass', 1, GETDATE(), N'admin'),
  (66, N'Godswill Akpabio International Stadium', N'Godswill Akpabio Stadium', N'Uyo', 22, 30000, N'Uyo, Akwa Ibom State', N'https://upload.wikimedia.org/wikipedia/commons/a/a7/Godswill_Akpabio_Stadium.jpg', '2014-11-15', N'Grass', 1, GETDATE(), N'admin'),

  -- Egypt (country_id: 23)
  (67, N'Cairo International Stadium', N'Cairo International Stadium', N'Cairo', 23, 75000, N'Nasr City, Cairo', N'https://upload.wikimedia.org/wikipedia/commons/f/f4/Cairo_International_Stadium.jpg', '1960-07-23', N'Grass', 1, GETDATE(), N'admin'),
  (68, N'Borg El Arab Stadium', N'Borg El Arab Stadium', N'Alexandria', 23, 86000, N'Borg El Arab, Alexandria', N'https://upload.wikimedia.org/wikipedia/commons/8/8c/Borg_El_Arab_Stadium.jpg', '2007-02-15', N'Grass', 1, GETDATE(), N'admin'),
  (69, N'Al Ahly WE Al Salam Stadium', N'Al Ahly Stadium', N'Cairo', 23, 30000, N'New Administrative Capital, Cairo', N'https://upload.wikimedia.org/wikipedia/commons/3/35/Al_Ahly_Stadium.jpg', '2021-06-24', N'Grass', 1, GETDATE(), N'admin'),

  -- Saudi Arabia (country_id: 24)
  (70, N'King Fahd International Stadium', N'King Fahd Stadium', N'Riyadh', 24, 68752, N'Al Faisaliyah District, Riyadh', N'https://upload.wikimedia.org/wikipedia/commons/0/00/King_Fahd_Stadium.jpg', '1987-11-29', N'Grass', 1, GETDATE(), N'admin'),
  (71, N'King Abdullah Sports City Stadium', N'King Abdullah Sports City', N'Jeddah', 24, 62345, N'Al Naseem District, Jeddah', N'https://upload.wikimedia.org/wikipedia/commons/7/79/King_Abdullah_Sports_City.jpg', '2014-05-01', N'Grass', 1, GETDATE(), N'admin'),
  (72, N'Prince Faisal bin Fahd Stadium', N'Prince Faisal Stadium', N'Riyadh', 24, 22500, N'Al-Malaz District, Riyadh', N'https://upload.wikimedia.org/wikipedia/commons/c/c2/Prince_Faisal_Stadium.jpg', '1971-01-01', N'Grass', 1, GETDATE(), N'admin'),

  -- United Arab Emirates (country_id: 25)
  (73, N'Hazza Bin Zayed Stadium', N'Hazza Bin Zayed Stadium', N'Al Ain', 25, 25000, N'Al Ain, Abu Dhabi', N'https://upload.wikimedia.org/wikipedia/commons/4/42/Hazza_Bin_Zayed_Stadium.jpg', '2014-01-21', N'Grass', 1, GETDATE(), N'admin'),
  (74, N'Mohammed Bin Zayed Stadium', N'Mohammed Bin Zayed Stadium', N'Abu Dhabi', 25, 42056, N'Mohammed Bin Zayed City, Abu Dhabi', N'https://upload.wikimedia.org/wikipedia/commons/9/9f/MBZ_Stadium.jpg', '2008-05-08', N'Grass', 1, GETDATE(), N'admin'),
  (75, N'Al Maktoum Stadium', N'Al Maktoum Stadium', N'Dubai', 25, 15000, N'Al Nasr, Dubai', N'https://upload.wikimedia.org/wikipedia/commons/a/a5/Al_Maktoum_Stadium.jpg', '1996-01-01', N'Grass', 1, GETDATE(), N'admin'),

  -- Argentina (country_id: 26)
  (76, N'Estadio Mas Monumental', N'Estadio Monumental', N'Buenos Aires', 26, 84567, N'Av. Figueroa Alcorta 7597, Buenos Aires', N'https://upload.wikimedia.org/wikipedia/commons/3/3f/Estadio_Monumental.jpg', '1938-05-26', N'Grass', 1, GETDATE(), N'admin'),
  (77, N'La Bombonera', N'La Bombonera', N'Buenos Aires', 26, 54000, N'Brandsen 805, La Boca, Buenos Aires', N'https://upload.wikimedia.org/wikipedia/commons/3/35/La_Bombonera.jpg', '1940-05-25', N'Grass', 1, GETDATE(), N'admin'),
  (78, N'Estadio Mario Alberto Kempes', N'Estadio Kempes', N'Cordoba', 26, 57000, N'Av. del Libertador Simon Bolivar, Cordoba', N'https://upload.wikimedia.org/wikipedia/commons/b/b4/Estadio_Kempes.jpg', '1978-05-28', N'Grass', 1, GETDATE(), N'admin');

SET IDENTITY_INSERT venue OFF;
