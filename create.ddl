
    create table t_city (
        population integer not null,
        cityId bigserial not null,
        cityName varchar(255),
        country varchar(255),
        description varchar(255),
        imageUrl varchar(255),
        primary key (cityId)
    );

    create table t_review (
        rating integer not null,
        cityId bigint,
        reviewID bigserial not null,
        userId bigint,
        review varchar(255),
        primary key (reviewID)
    );

    create table t_user (
        userId bigserial not null,
        apiKey uuid,
        email varchar(255),
        firstName varchar(255),
        lastName varchar(255),
        primary key (userId)
    );

    alter table if exists t_review 
       add constraint FK949c779wyl8qdjh7ked94ljyj 
       foreign key (cityId) 
       references t_city;

    alter table if exists t_review 
       add constraint FK1mxophnet0pihvbtbru4ated1 
       foreign key (userId) 
       references t_user;
insert into t_city (cityName, country, description, population, imageUrl) values  ('Stockholm', 'Sweden', 'Stockholm, the capital of Sweden, encompasses 14 islands and more than 50 bridges on an extensive Baltic Sea archipelago.', '975904', 'https://media.timeout.com/images/105171709/750/422/image.jpg'), ('New York City', 'United States', 'New York City comprises 5 boroughs sitting where the Hudson River meets the Atlantic Ocean.', '8336817', 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/34/Upwardly-motile-in-NYC.jpg/1920px-Upwardly-motile-in-NYC.jpg'), ('Paris', 'France', 'Paris, France''s capital, is a major European city and a global center for art, fashion, gastronomy, and culture.', '2148271', 'https://sevardheter.se/wp-content/uploads/2019/10/Paris-sevardheter-och-saker-att-gora-800x533px.jpg'), ('Tokyo', 'Japan', 'Tokyo, Japan''s bustling capital, mixes the ultramodern and the traditional.', '13515271', 'https://media.cntraveler.com/photos/60341fbad7bd3b27823c9db2/4:3/w_4624,h_3468,c_limit/Tokyo-2021-GettyImages-1208124099.jpg'), ('London', 'United Kingdom', 'London, the capital of England and the United Kingdom, is a 21st-century city with history stretching back to Roman times.', '8982000', 'https://upload.wikimedia.org/wikipedia/commons/thumb/e/e4/Palace_of_Westminster_from_the_dome_on_Methodist_Central_Hall_%28cropped%29.jpg/900px-Palace_of_Westminster_from_the_dome_on_Methodist_Central_Hall_%28cropped%29.jpg'), ('Rome', 'Italy', 'Rome, Italy''s capital, is a sprawling, cosmopolitan city with nearly 3,000 years of globally influential art, architecture, and culture.', '2872800', 'https://sevardheter.se/wp-content/uploads/2019/09/Rom-sevardheter-och-saker-att-gora-800x533px.jpg'), ('Sydney', 'Australia', 'Sydney, capital of New South Wales and one of Australia''s largest cities, is best known for its harbourfront Sydney Opera House.', '5312163', 'https://i.natgeofe.com/n/bd48279e-be5a-4f28-9551-5cb917c6766e/GettyImages-103455489cropped.jpg'), ('Beijing', 'China', 'Beijing, China’s sprawling capital, has history stretching back 3 millennia.', '21542000', 'https://cdn.britannica.com/03/198203-050-138BB1C3/entrance-Gate-of-Divine-Might-Beijing-Forbidden.jpg'), ('Los Angeles', 'United States', 'Los Angeles is a sprawling Southern California city famed as the center of the nation’s film and television industry.', '3971883', 'https://cdn.britannica.com/22/154122-050-B1D0A7FD/Skyline-Los-Angeles-California.jpg'), ('Berlin', 'Germany', 'Berlin, Germany’s capital and cultural center, dates to the 13th century.', '3769495', 'https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Museumsinsel_Berlin_Juli_2021_1_%28cropped%29.jpg/1200px-Museumsinsel_Berlin_Juli_2021_1_%28cropped%29.jpg'), ('Moscow', 'Russia', 'Moscow, on the Moskva River in western Russia, is the nation’s cosmopolitan capital.', '12678079', 'https://www.iventustravel.se/upload/images/cm3-nya_hemsidan/huvudbild-startbannerstorbild-600x338/moskva-vasilijkatedralen.jpg'), ('Mexico City', 'Mexico', 'Mexico City is the densely populated, high-altitude capital of Mexico.', '9209944', 'https://www.travelandleisure.com/thmb/Z1DZUlN4Q3YMRo59fr-CSA3hVmw=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/TAL-mexico-city-lead-MEXICOCITYTG01223-99370d83a62f4d87af9a6e44636ebb07.jpg'), ('Toronto', 'Canada', 'Toronto, the capital of the province of Ontario, is a major Canadian city along Lake Ontario’s northwestern shore.', '2731571', 'https://cdn.britannica.com/93/94493-050-35524FED/Toronto.jpg'), ('Cairo', 'Egypt', 'Cairo, Egypt''s sprawling capital, is set on the Nile River.', '9119149', 'https://media.cntraveler.com/photos/655cdf1d2d09a7e0b27741b5/16:9/w_2560%2Cc_limit/Cairo%2520Egypt_GettyImages-1370918272.jpg'), ('Mumbai', 'India', 'Mumbai (formerly called Bombay) is a densely populated city on India’s west coast.', '20411274', 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/33/F7xZ48abwAAgNst.jpg/800px-F7xZ48abwAAgNst.jpg'), ('Bangkok', 'Thailand', 'Bangkok, Thailand’s capital, is a large city known for ornate shrines and vibrant street life.', '8280925', 'https://a.cdn-hotels.com/gdcs/production172/d459/3af9262b-3d8b-40c6-b61d-e37ae1aa90aa.jpg'), ('Istanbul', 'Turkey', 'Istanbul is a major city in Turkey that straddles Europe and Asia across the Bosphorus Strait.', '15029231', 'https://a.cdn-hotels.com/gdcs/production6/d781/3bae040b-2afb-4b11-9542-859eeb8ebaf1.jpg'), ('Seoul', 'South Korea', 'Seoul, the capital of South Korea, is a huge metropolis where modern skyscrapers, high-tech subways, and pop culture meet Buddhist temples, palaces, and street markets.', '9995784', 'https://upload.wikimedia.org/wikipedia/commons/7/7f/%EB%8F%84%EC%8B%9C%EC%99%80%EC%9D%98_%EA%B3%B5%EC%A1%B4.jpg'), ('Dubai', 'United Arab Emirates', 'Dubai is a city and emirate in the United Arab Emirates known for luxury shopping, ultramodern architecture, and a lively nightlife scene.', '3331000', 'https://www.apollo.se/upload/inspiration/restips/emiraten/uae-dubai-palm-jumeirah-900x600.jpg');
insert into t_user (apiKey,firstName,lastName, email) values ('84dddfe1-9c42-4a50-9fb2-c4d8640468c6','jacob','jansson','jacobjacob@hej.com');
