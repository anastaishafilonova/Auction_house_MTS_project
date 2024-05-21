create table auction(
	productid bigint primary key not null,
	status text not null,
	starttime timestamp not null,
	endtime timestamp not null,
	minbet integer not null,
 	curprice integer not null,
	customerid bigint not null,
	sellerid bigint not null
);
