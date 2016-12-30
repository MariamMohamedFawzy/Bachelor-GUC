# BachelorVesrions

##Setup
1. Download the data from [Yelp Dataset Challenge](https://www.yelp.com/dataset_challenge)
2. Put the businesses file in the src directory with name : yelp_academic_dataset_business.json
3. split the reviews json file into files : xaa, xab, xac, xad, xae, xaf, xag, xah, xai, xaj, xak, xal and xan.
4. put the reviews files into reviews folder in the src directory.
5. Add the jars in the lib folder.
6. Write true instead of false in start.txt file in startProject folder.
7. Create a database with the name: BachelorDB.
8. Modify the database connections in the file : hibernate.cfg.xml in the src directory.

##Database tables:
###Reviews table
```
CREATE TABLE `review` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `review_text` text,
  `user_id` varchar(50) DEFAULT NULL,
  `business_id` varchar(200) DEFAULT NULL,
  `review_id` varchar(50) DEFAULT NULL,
  `votes_funny` int(11) DEFAULT NULL,
  `votes_useful` int(11) DEFAULT NULL,
  `votes_cool` int(11) DEFAULT NULL,
  `stars` int(11) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `topic_value` double unsigned zerofill DEFAULT NULL,
  `sentiment_review_text` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2685067 DEFAULT CHARSET=utf8;
```

#Businesses table
```
CREATE TABLE `business` (
  `id` int(11) NOT NULL,
  `business_id` varchar(100) DEFAULT '',
  `full_address` text,
  `open` tinyint(1) DEFAULT NULL,
  `name` text,
  `state` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `stars` double DEFAULT NULL,
  `review_count` int(11) DEFAULT NULL,
  `hours_mon_close` varchar(50) DEFAULT NULL,
  `hours_mon_open` varchar(50) DEFAULT NULL,
  `hours_tues_close` varchar(50) DEFAULT NULL,
  `hours_tues_open` varchar(50) DEFAULT NULL,
  `hours_wed_close` varchar(50) DEFAULT NULL,
  `hours_wed_open` varchar(50) DEFAULT NULL,
  `hours_thu_close` varchar(50) DEFAULT NULL,
  `hours_thu_open` varchar(50) DEFAULT NULL,
  `hours_fri_close` varchar(50) DEFAULT NULL,
  `hours_fri_open` varchar(50) DEFAULT NULL,
  `attr_take_out` tinyint(1) DEFAULT NULL,
  `attr_drive_thru` tinyint(1) DEFAULT NULL,
  `attr_caters` tinyint(1) DEFAULT NULL,
  `attr_noise_level` varchar(50) DEFAULT NULL,
  `attr_takes_reservations` tinyint(1) DEFAULT NULL,
  `attr_delivery` double DEFAULT NULL,
  `attr_has_tv` tinyint(1) DEFAULT NULL,
  `attr_outdoor_seating` tinyint(1) DEFAULT NULL,
  `attr_attire` varchar(100) DEFAULT NULL,
  `attr_alcohol` varchar(100) DEFAULT NULL,
  `attr_waiter_service` tinyint(1) DEFAULT NULL,
  `attr_accepts_credit_cards` tinyint(1) DEFAULT NULL,
  `attr_good_for_kids` tinyint(1) DEFAULT NULL,
  `attr_good_for_groups` tinyint(1) DEFAULT NULL,
  `attr_price_range` int(11) DEFAULT NULL,
  `attr_ambience_romantic` tinyint(1) DEFAULT NULL,
  `attr_ambience_intimate` tinyint(1) DEFAULT NULL,
  `attr_ambience_classy` tinyint(1) DEFAULT NULL,
  `attr_ambience_hipster` tinyint(1) DEFAULT NULL,
  `attr_ambience_divey` tinyint(1) DEFAULT NULL,
  `attr_ambience_touristy` tinyint(1) DEFAULT NULL,
  `attr_ambience_trendy` tinyint(1) DEFAULT NULL,
  `attr_ambience_upscale` tinyint(1) DEFAULT NULL,
  `attr_ambience_casual` tinyint(1) DEFAULT NULL,
  `attr_good_for_dessert` tinyint(1) DEFAULT NULL,
  `attr_good_for_latenight` tinyint(1) DEFAULT NULL,
  `attr_good_for_lunch` tinyint(1) DEFAULT NULL,
  `attr_good_for_dinner` tinyint(1) DEFAULT NULL,
  `attr_good_for_brunch` tinyint(1) DEFAULT NULL,
  `attr_good_for_breakfast` tinyint(1) DEFAULT NULL,
  `attr_parking_garage` tinyint(1) DEFAULT NULL,
  `attr_parking_street` tinyint(1) DEFAULT NULL,
  `attr_parking_validated` tinyint(1) DEFAULT NULL,
  `attr_parking_lot` tinyint(1) DEFAULT NULL,
  `attr_parking_valet` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

#Category table
```
CREATE TABLE `category` (
  `name` varchar(100) DEFAULT NULL,
  `business_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=255698 DEFAULT CHARSET=utf8;
```