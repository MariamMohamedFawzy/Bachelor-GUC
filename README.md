# BachelorVesrions

##Setup
1. Download the data from [Yelp Dataset Challenge](https://www.yelp.com/dataset_challenge)
2. Put the businesses file in the src directory with name : yelp_academic_dataset_business.json
3. split the reviews json file into files : xaa, xab, xac, xad, xae, xaf, xag, xah, xai, xaj, xak, xal and xan.
4. put the reviews files into reviews folder in the src directory.
5. Add the libraries as in the libraries section.
6. Write true instead of false in start.txt file in startProject folder.
7. Create a database with the name: BachelorDB.
8. Modify the database connections in the file : hibernate.cfg.xml in the src directory.

## Libraries

### Libraries for version 1 :

antlr-2.7.7.jar					hibernate-core-5.2.4.Final.jar			jboss-marshalling-osgi-1.4.10.Final.jar		org.osgi.compendium-4.3.1.jar
apache-commons 2.jar				hibernate-ehcache-5.2.4.Final.jar		jboss-transaction-api_1.1_spec-1.0.1.Final.jar	org.osgi.core-4.3.1.jar
c3p0-0.9.2.1.jar				hibernate-envers-5.2.4.Final.jar		jgroups-3.6.7.Final.jar				pdf.jar
cdi-api-1.1.jar					hibernate-infinispan-5.2.4.Final-tests.jar	jna-4.1.0.jar					postgresql-9.4-1200-jdbc41.jar
cglib-2.2.jar					hibernate-infinispan-5.2.4.Final.jar		jna-platform-4.1.0.jar				proxool-0.8.3.jar
cglib-2.2.jar.zip				hibernate-jpa-2.1-api-1.0.0.Final.jar		jsoup-1.7.2.jar					slf4j-api-1.7.21.jar
classmate-1.3.0.jar				hibernate-jpamodelgen-5.2.4.Final.jar		jsr250-api-1.0.jar				slf4j-api-1.7.7.jar
core.jar					hibernate-osgi-5.2.4.Final-karaf.xml		jts-1.13.jar					slf4j-simple-1.7.21.jar
cue.language.jar				hibernate-osgi-5.2.4.Final.jar			junit-4.8.2.jar					slf4j-simple-1.7.7.jar
dom4j-1.6.1.jar					hibernate-proxool-5.2.4.Final.jar		junit-dep-4.8.2.jar				stanford-corenlp-3.6.0-models.jar
dom4j.jar					hibernate-spatial-5.2.4.Final.jar		jxmaps-1.1.jar					stanford-corenlp-3.6.0-sources.jar
ehcache-2.10.3.jar				infinispan-commons-8.2.3.Final.jar		jxmaps-linux32-1.1.jar				stanford-corenlp-3.6.0.jar
el-api-2.2.jar					infinispan-core-8.2.3.Final.jar			jxmaps-linux64-1.1.jar				synthetica.jar
geolatte-geom-1.0.6.jar				itext.jar					jxmaps-mac-1.1.jar				syntheticaAluOxide.jar
geronimo-jta_1.1_spec-1.1.1.jar			jandex-2.0.0.Final.jar				jxmaps-win-1.1.jar				syntheticaBlackEye.jar
glazedlists_java15-1.9.1.jar			javassist-3.20.0-GA.jar				license.jar					syntheticaBlueLight.jar
gson-2.2.2.jar					javassist.jar					log4j-1.2.17.jar				waffle-jna-1.7.jar
guava-18.0.jar					javax.inject-1.jar				mchange-commons-java-0.2.3.4.jar		xalan.jar
hibernate-c3p0-5.2.4.Final.jar			jboss-interceptors-api_1.1_spec-1.0.0.Beta1.jar	mockito-all-1.8.5.jar
hibernate-commons-annotations-5.0.1.Final.jar	jboss-logging-3.3.0.Final.jar			mysql-connector-java-5.1.40-bin.jar

### Libraries used in version 2 :

antlr-2.7.7.jar					hibernate-core-5.2.4.Final.jar			jboss-marshalling-osgi-1.4.10.Final.jar		opennlp-tools-1.6.0.jar
apache-commons 2.jar				hibernate-ehcache-5.2.4.Final.jar		jboss-transaction-api_1.1_spec-1.0.1.Final.jar	opennlp-uima-1.6.0.jar
c3p0-0.9.2.1.jar				hibernate-envers-5.2.4.Final.jar		jgroups-3.6.7.Final.jar				org.osgi.compendium-4.3.1.jar
cdi-api-1.1.jar					hibernate-infinispan-5.2.4.Final-tests.jar	jna-4.1.0.jar					org.osgi.core-4.3.1.jar
cglib-2.2.jar					hibernate-infinispan-5.2.4.Final.jar		jna-platform-4.1.0.jar				pdf.jar
cglib-2.2.jar.zip				hibernate-jpa-2.1-api-1.0.0.Final.jar		jsoup-1.7.2.jar					postgresql-9.4-1200-jdbc41.jar
classmate-1.3.0.jar				hibernate-jpamodelgen-5.2.4.Final.jar		jsr250-api-1.0.jar				proxool-0.8.3.jar
core.jar					hibernate-osgi-5.2.4.Final-karaf.xml		jts-1.13.jar					slf4j-api-1.7.21.jar
cue.language.jar				hibernate-osgi-5.2.4.Final.jar			junit-4.8.2.jar					slf4j-api-1.7.7.jar
dom4j-1.6.1.jar					hibernate-proxool-5.2.4.Final.jar		junit-dep-4.8.2.jar				slf4j-simple-1.7.21.jar
dom4j.jar					hibernate-spatial-5.2.4.Final.jar		jxmaps-1.1.jar					slf4j-simple-1.7.7.jar
ehcache-2.10.3.jar				infinispan-commons-8.2.3.Final.jar		jxmaps-linux32-1.1.jar				stanford-corenlp-3.6.0-models.jar
el-api-2.2.jar					infinispan-core-8.2.3.Final.jar			jxmaps-linux64-1.1.jar				stanford-corenlp-3.6.0-sources.jar
geolatte-geom-1.0.6.jar				itext.jar					jxmaps-mac-1.1.jar				stanford-corenlp-3.6.0.jar
geronimo-jta_1.1_spec-1.1.1.jar			jandex-2.0.0.Final.jar				jxmaps-win-1.1.jar				synthetica.jar
glazedlists_java15-1.9.1.jar			javassist-3.20.0-GA.jar				license.jar					syntheticaAluOxide.jar
gson-2.2.2.jar					javassist.jar					log4j-1.2.17.jar				syntheticaBlackEye.jar
guava-18.0.jar					javax.inject-1.jar				mchange-commons-java-0.2.3.4.jar		syntheticaBlueLight.jar
hibernate-c3p0-5.2.4.Final.jar			jboss-interceptors-api_1.1_spec-1.0.0.Beta1.jar	mockito-all-1.8.5.jar				waffle-jna-1.7.jar
hibernate-commons-annotations-5.0.1.Final.jar	jboss-logging-3.3.0.Final.jar			mysql-connector-java-5.1.40-bin.jar		xalan.jar

### Libraries used in version 3 :

antlr-2.7.7.jar					hibernate-core-5.2.4.Final.jar			jboss-marshalling-osgi-1.4.10.Final.jar		org.osgi.compendium-4.3.1.jar
apache-commons 2.jar				hibernate-ehcache-5.2.4.Final.jar		jboss-transaction-api_1.1_spec-1.0.1.Final.jar	org.osgi.core-4.3.1.jar
c3p0-0.9.2.1.jar				hibernate-envers-5.2.4.Final.jar		jgroups-3.6.7.Final.jar				pdf.jar
cdi-api-1.1.jar					hibernate-infinispan-5.2.4.Final-tests.jar	jna-4.1.0.jar					postgresql-9.4-1200-jdbc41.jar
cglib-2.2.jar					hibernate-infinispan-5.2.4.Final.jar		jna-platform-4.1.0.jar				proxool-0.8.3.jar
cglib-2.2.jar.zip				hibernate-jpa-2.1-api-1.0.0.Final.jar		jsoup-1.7.2.jar					slf4j-api-1.7.21.jar
classmate-1.3.0.jar				hibernate-jpamodelgen-5.2.4.Final.jar		jsr250-api-1.0.jar				slf4j-api-1.7.7.jar
core.jar					hibernate-osgi-5.2.4.Final-karaf.xml		jts-1.13.jar					slf4j-simple-1.7.21.jar
cue.language.jar				hibernate-osgi-5.2.4.Final.jar			junit-4.8.2.jar					slf4j-simple-1.7.7.jar
dom4j-1.6.1.jar					hibernate-proxool-5.2.4.Final.jar		junit-dep-4.8.2.jar				stanford-corenlp-3.6.0-models.jar
dom4j.jar					hibernate-spatial-5.2.4.Final.jar		jxmaps-1.1.jar					stanford-corenlp-3.6.0-sources.jar
ehcache-2.10.3.jar				infinispan-commons-8.2.3.Final.jar		jxmaps-linux32-1.1.jar				stanford-corenlp-3.6.0.jar
el-api-2.2.jar					infinispan-core-8.2.3.Final.jar			jxmaps-linux64-1.1.jar				synthetica.jar
geolatte-geom-1.0.6.jar				itext.jar					jxmaps-mac-1.1.jar				syntheticaAluOxide.jar
geronimo-jta_1.1_spec-1.1.1.jar			jandex-2.0.0.Final.jar				jxmaps-win-1.1.jar				syntheticaBlackEye.jar
glazedlists_java15-1.9.1.jar			javassist-3.20.0-GA.jar				license.jar					syntheticaBlueLight.jar
gson-2.2.2.jar					javassist.jar					log4j-1.2.17.jar				waffle-jna-1.7.jar
guava-18.0.jar					javax.inject-1.jar				mchange-commons-java-0.2.3.4.jar		xalan.jar
hibernate-c3p0-5.2.4.Final.jar			jboss-interceptors-api_1.1_spec-1.0.0.Beta1.jar	mockito-all-1.8.5.jar
hibernate-commons-annotations-5.0.1.Final.jar	jboss-logging-3.3.0.Final.jar			mysql-connector-java-5.1.40-bin.jar

### Libraries used in version 4 :

antlr-2.7.7.jar					hibernate-core-5.2.4.Final.jar			jboss-marshalling-osgi-1.4.10.Final.jar		opennlp-tools-1.6.0.jar
apache-commons 2.jar				hibernate-ehcache-5.2.4.Final.jar		jboss-transaction-api_1.1_spec-1.0.1.Final.jar	opennlp-uima-1.6.0.jar
c3p0-0.9.2.1.jar				hibernate-envers-5.2.4.Final.jar		jgroups-3.6.7.Final.jar				org.osgi.compendium-4.3.1.jar
cdi-api-1.1.jar					hibernate-infinispan-5.2.4.Final-tests.jar	jna-4.1.0.jar					org.osgi.core-4.3.1.jar
cglib-2.2.jar					hibernate-infinispan-5.2.4.Final.jar		jna-platform-4.1.0.jar				pdf.jar
cglib-2.2.jar.zip				hibernate-jpa-2.1-api-1.0.0.Final.jar		jsoup-1.7.2.jar					postgresql-9.4-1200-jdbc41.jar
classmate-1.3.0.jar				hibernate-jpamodelgen-5.2.4.Final.jar		jsr250-api-1.0.jar				proxool-0.8.3.jar
core.jar					hibernate-osgi-5.2.4.Final-karaf.xml		jts-1.13.jar					slf4j-api-1.7.21.jar
cue.language.jar				hibernate-osgi-5.2.4.Final.jar			junit-4.8.2.jar					slf4j-api-1.7.7.jar
dom4j-1.6.1.jar					hibernate-proxool-5.2.4.Final.jar		junit-dep-4.8.2.jar				slf4j-simple-1.7.21.jar
dom4j.jar					hibernate-spatial-5.2.4.Final.jar		jxmaps-1.1.jar					slf4j-simple-1.7.7.jar
ehcache-2.10.3.jar				infinispan-commons-8.2.3.Final.jar		jxmaps-linux32-1.1.jar				stanford-corenlp-3.6.0-models.jar
el-api-2.2.jar					infinispan-core-8.2.3.Final.jar			jxmaps-linux64-1.1.jar				stanford-corenlp-3.6.0-sources.jar
geolatte-geom-1.0.6.jar				itext.jar					jxmaps-mac-1.1.jar				stanford-corenlp-3.6.0.jar
geronimo-jta_1.1_spec-1.1.1.jar			jandex-2.0.0.Final.jar				jxmaps-win-1.1.jar				synthetica.jar
glazedlists_java15-1.9.1.jar			javassist-3.20.0-GA.jar				license.jar					syntheticaAluOxide.jar
gson-2.2.2.jar					javassist.jar					log4j-1.2.17.jar				syntheticaBlackEye.jar
guava-18.0.jar					javax.inject-1.jar				mchange-commons-java-0.2.3.4.jar		syntheticaBlueLight.jar
hibernate-c3p0-5.2.4.Final.jar			jboss-interceptors-api_1.1_spec-1.0.0.Beta1.jar	mockito-all-1.8.5.jar				waffle-jna-1.7.jar
hibernate-commons-annotations-5.0.1.Final.jar	jboss-logging-3.3.0.Final.jar			mysql-connector-java-5.1.40-bin.jar		xalan.jar

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