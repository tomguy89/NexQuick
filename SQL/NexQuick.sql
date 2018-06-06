DROP TABLE CHATINFO;
DROP TABLE FAVORITEINFO;
DROP TABLE QPACCOUNT;
DROP TABLE QPSCORE;
DROP TABLE QPPOSITION;
DROP TABLE FREIGHTINFO;
DROP TABLE ORDERINFO;
DROP TABLE CALLINFO;
DROP TABLE PRICEINFO;
DROP TABLE QPINFO;
DROP TABLE CSINFO;
DROP SEQUENCE CNUMSEQ;
DROP SEQUENCE FNUMSEQ;
DROP SEQUENCE FANUMSEQ;
DROP SEQUENCE ONUMSEQ;
DROP SEQUENCE QPIDSEQ;

CREATE SEQUENCE CNUMSEQ;
CREATE SEQUENCE FNUMSEQ;
CREATE SEQUENCE FANUMSEQ;
CREATE SEQUENCE ONUMSEQ;
CREATE SEQUENCE QPIDSEQ;

/* 가격정보 */
CREATE TABLE priceInfo(
    freighttype NUMBER NOT NULL,
    freightName VARCHAR2(30) NOT NULL,
    freeCount NUMBER(5) NOT NULL,
    price NUMBER NOT NULL
);
CREATE UNIQUE INDEX PK_priceinfo
	ON priceinfo (
		freighttype ASC
	);

ALTER TABLE priceinfo
	ADD
		CONSTRAINT PK_priceinfo
		PRIMARY KEY (
			freighttype
		);


/* 배달원 */
CREATE TABLE qpinfo (
	qpId NUMBER NOT NULL, /* qpId */
	qpPassword VARCHAR2(30) NOT NULL, /* qpPassword */
	qpName VARCHAR2(15) NOT NULL, /* qpName */
	qpPhone VARCHAR2(15) NOT NULL, /* qpPhone */
	qpLicense VARCHAR(300) NOT NULL, /* qpLicense */
	qpVehicleType NUMBER NOT NULL, /* qpVehicleType */
    qpDeposit NUMBER NOT NULL,
	qpProfile VARCHAR2(500) /* qpProfile */
);

COMMENT ON TABLE qpinfo IS '배달원';

COMMENT ON COLUMN qpinfo.qpId IS 'qpId';

COMMENT ON COLUMN qpinfo.qpPassword IS 'qpPassword';

COMMENT ON COLUMN qpinfo.qpName IS 'qpName';

COMMENT ON COLUMN qpinfo.qpPhone IS 'qpPhone';

COMMENT ON COLUMN qpinfo.qpLicense IS 'qpLicense';

COMMENT ON COLUMN qpinfo.qpVehicleType IS 'qpVehicleType';

COMMENT ON COLUMN qpinfo.qpDeposit IS 'qpDeposit';

COMMENT ON COLUMN qpinfo.qpProfile IS 'qpProfile';

CREATE UNIQUE INDEX PK_qpinfo
	ON qpinfo (
		qpId ASC
	);

ALTER TABLE qpinfo
	ADD
		CONSTRAINT PK_qpinfo
		PRIMARY KEY (
			qpId
		);

/* 고객 */
CREATE TABLE csinfo (
	csId VARCHAR2(30) NOT NULL, /* csId */
	csPassword VARCHAR2(30) NOT NULL, /* csPassword */
	csName VARCHAR2(15) NOT NULL, /* csName */
	csPhone VARCHAR2(15) NOT NULL, /* csPhone */
	csType NUMBER NOT NULL, /* csType */
	csBusinessName VARCHAR2(200), /* csBusinessName */
	csBusinessNumber VARCHAR2(200), /* csBusinessNumber */
	csDepartment VARCHAR2(200), /* csDepartment */
	csMilege NUMBER NOT NULL, /* csMilege */
	csGrade NUMBER NOT NULL /* csGrade */
);

COMMENT ON TABLE csinfo IS '고객';

COMMENT ON COLUMN csinfo.csId IS 'csId';

COMMENT ON COLUMN csinfo.csPassword IS 'csPassword';

COMMENT ON COLUMN csinfo.csName IS 'csName';

COMMENT ON COLUMN csinfo.csPhone IS 'csPhone';

COMMENT ON COLUMN csinfo.csType IS 'csType';

COMMENT ON COLUMN csinfo.csBusinessName IS 'csBusinessName';

COMMENT ON COLUMN csinfo.csBusinessNumber IS 'csBusinessNumber';

COMMENT ON COLUMN csinfo.csDepartment IS 'csDepartment';

COMMENT ON COLUMN csinfo.csMilege IS 'csMilege';

COMMENT ON COLUMN csinfo.csGrade IS '권한부여';

CREATE UNIQUE INDEX PK_csinfo
	ON csinfo (
		csId ASC
	);

ALTER TABLE csinfo
	ADD
		CONSTRAINT PK_csinfo
		PRIMARY KEY (
			csId
		);

/* 콜 */
CREATE TABLE callinfo (
	callNum NUMBER NOT NULL, /* callNum */
	csId VARCHAR2(30) NOT NULL, /* csId */
	qpId NUMBER, /* qpId */
	senderName VARCHAR2(30) NOT NULL, /* senderName */
	senderAddress VARCHAR2(500) NOT NULL, /* senderAddress */
    senderAddressDetail VARCHAR2(500) NOT NULL,
	senderPhone VARCHAR2(15) NOT NULL, /* senderPhone */
	vehicleType NUMBER NOT NULL, /* vehicleType */
	totalPrice NUMBER NOT NULL, /* totalPrice */
	payType NUMBER NOT NULL, /* payType */
	urgent NUMBER NOT NULL, /* urgent */
	series NUMBER NOT NULL, /* series */
	reserved NUMBER NOT NULL, /* reserved */
	reservationTime DATE, /* reservationTime */
	deliverystatus NUMBER NOT NULL, /* deliverystatus */
	paystatus NUMBER NOT NULL, /* paystatus */
	callTime DATE NOT NULL /* callTime */
);

COMMENT ON TABLE callinfo IS '콜';

COMMENT ON COLUMN callinfo.callNum IS 'callNum';

COMMENT ON COLUMN callinfo.csId IS 'csId';

COMMENT ON COLUMN callinfo.qpId IS 'qpId';

COMMENT ON COLUMN callinfo.senderName IS 'senderName';

COMMENT ON COLUMN callinfo.senderAddress IS 'senderAddress';

COMMENT ON COLUMN callinfo.senderAddressDetail IS 'senderAddressDetail';

COMMENT ON COLUMN callinfo.senderPhone IS 'senderPhone';

COMMENT ON COLUMN callinfo.vehicleType IS '운송수단테이블';

COMMENT ON COLUMN callinfo.totalPrice IS 'totalPrice';

COMMENT ON COLUMN callinfo.payType IS 'payType';

COMMENT ON COLUMN callinfo.urgent IS '긴급배송여부';

COMMENT ON COLUMN callinfo.series IS '묶음배송여부';

COMMENT ON COLUMN callinfo.reserved IS '예약배송여부';

COMMENT ON COLUMN callinfo.reservationTime IS '예약배송날짜시간';

COMMENT ON COLUMN callinfo.deliverystatus IS '배달진행상황테이블';

COMMENT ON COLUMN callinfo.paystatus IS '결제진행상황테이블';

COMMENT ON COLUMN callinfo.callTime IS '콜들어온시간';

CREATE UNIQUE INDEX PK_callinfo
	ON callinfo (
		callNum ASC
	);

ALTER TABLE callinfo
	ADD
		CONSTRAINT PK_callinfo
		PRIMARY KEY (
			callNum
		);

/* 채팅 */
CREATE TABLE chatinfo (
	csId VARCHAR2(30) NOT NULL, /* csId */
	chattingFile VARCHAR2(500) /* chattingFile */
);

COMMENT ON TABLE chatinfo IS '채팅';

COMMENT ON COLUMN chatinfo.csId IS 'csId';

COMMENT ON COLUMN chatinfo.chattingFile IS '이건여쭤보기...';

CREATE UNIQUE INDEX PK_chatinfo
	ON chatinfo (
		csId ASC
	);

ALTER TABLE chatinfo
	ADD
		CONSTRAINT PK_chatinfo
		PRIMARY KEY (
			csId
		);

/* 주소리스트 */
CREATE TABLE FavoriteInfo (
	favoriteId NUMBER NOT NULL, /* addId */
	csId VARCHAR2(30) NOT NULL, /* csId */
	AddressType NUMBER NOT NULL, /* AddressType */
	Address VARCHAR2(500) NOT NULL, /* Address */
    addrDetail VARCHAR2(500),
    receiverName VARCHAR2(30) NOT NULL, /* receiverName */
	receiverPhone VARCHAR2(15) NOT NULL /* receiverPhone */
);

COMMENT ON TABLE favoriteInfo IS '즐겨찾기 리스트';

COMMENT ON COLUMN favoriteInfo.favoriteId IS 'addId';

COMMENT ON COLUMN favoriteInfo.csId IS 'csId';

COMMENT ON COLUMN favoriteInfo.AddressType IS 'AddressType';

COMMENT ON COLUMN favoriteInfo.Address IS 'Address';

COMMENT ON COLUMN favoriteInfo.receiverName IS 'receiverName';

COMMENT ON COLUMN favoriteInfo.receiverPhone IS 'receiverPhone';

CREATE UNIQUE INDEX PK_FavoriteInfo
	ON FavoriteInfo (
		favoriteId ASC
	);

ALTER TABLE FavoriteInfo
	ADD
		CONSTRAINT PK_FavoriteInfo
		PRIMARY KEY (
			favoriteId
		);

/* 주문 */
CREATE TABLE orderInfo (
	orderNum NUMBER NOT NULL, /* orderNum */
	callNum NUMBER NOT NULL, /* callNum */
	receiverName VARCHAR2(30) NOT NULL, /* receiverName */
	receiverAddress VARCHAR2(500) NOT NULL, /* receiverAddress */
	receiverPhone VARCHAR2(15) NOT NULL, /* receiverPhone */
	orderPrice NUMBER NOT NULL, /* orderPrice */
    isget NUMBER NOT NULL, /* deliverystatus */
	arrivalTime DATE, /* arrivalTime */
	memo VARCHAR2(300) /* memo */
);

COMMENT ON TABLE orderInfo IS '주문';

COMMENT ON COLUMN orderInfo.orderNum IS 'orderNum';

COMMENT ON COLUMN orderInfo.callNum IS 'callNum';

COMMENT ON COLUMN orderInfo.receiverName IS 'receiverName';

COMMENT ON COLUMN orderInfo.receiverAddress IS 'receiverAddress';

COMMENT ON COLUMN orderInfo.receiverPhone IS 'receiverPhone';

COMMENT ON COLUMN orderInfo.orderPrice IS 'orderPrice';

COMMENT ON COLUMN orderInfo.isget IS '수령여부';

COMMENT ON COLUMN orderInfo.arrivalTime IS 'arrivalTime';

COMMENT ON COLUMN orderInfo.memo IS 'memo';

CREATE UNIQUE INDEX PK_orderInfo
	ON orderInfo (
		orderNum ASC
	);

ALTER TABLE orderInfo
	ADD
		CONSTRAINT PK_orderInfo
		PRIMARY KEY (
			orderNum
		);

/* 위치 */
CREATE TABLE QPPosition (
	qpId NUMBER NOT NULL, /* qpId */
	qpLatitude NUMBER NOT NULL, /* qpLatitude */
	qpLongitude NUMBER NOT NULL, /* qpLongitude */
	bCode VARCHAR2(12) NOT NULL,
    hCode VARCHAR2(12) NOT NULL
);

COMMENT ON TABLE QPPosition IS '위치';

COMMENT ON COLUMN QPPosition.qpId IS 'qpId';

COMMENT ON COLUMN QPPosition.qpLatitude IS '위도';

COMMENT ON COLUMN QPPosition.qpLongitude IS '경도';

CREATE UNIQUE INDEX PK_QPPosition
	ON QPPosition (
		qpId ASC
	);

ALTER TABLE QPPosition
	ADD
		CONSTRAINT PK_QPPosition
		PRIMARY KEY (
			qpId
		);

/* 평점 */
CREATE TABLE QPScore (
	ratingNum NUMBER NOT NULL, /* ratingNum */
	qpId NUMBER NOT NULL, /* qpId */
	callNum NUMBER NOT NULL, /* callNum */
	orderNum NUMBER, /* orderNum */
	score NUMBER NOT NULL /* score */
);

COMMENT ON TABLE QPScore IS '평점';

COMMENT ON COLUMN QPScore.ratingNum IS 'ratingNum';

COMMENT ON COLUMN QPScore.qpId IS 'qpId';

COMMENT ON COLUMN QPScore.callNum IS 'callNum';

COMMENT ON COLUMN QPScore.orderNum IS 'orderNum';

COMMENT ON COLUMN QPScore.score IS 'score';

CREATE UNIQUE INDEX PK_QPScore
	ON QPScore (
		ratingNum ASC
	);

ALTER TABLE QPScore
	ADD
		CONSTRAINT PK_QPScore
		PRIMARY KEY (
			ratingNum
		);
        
CREATE TABLE qpAccount (
    qpId NUMBER NOT NULL,
    qpAccount VARCHAR2(30) NOT NULL,
    qpBank VARCHAR2(21) NOT NULL
);

ALTER TABLE qpAccount
	ADD
		CONSTRAINT FK_qpinfo_TO_qpAccount
		FOREIGN KEY (
			qpId
		)
		REFERENCES qpinfo (
			qpId
		);


/* 화물 */
CREATE TABLE freightInfo (
	freightNum NUMBER NOT NULL, /* freightNum */
	orderNum NUMBER NOT NULL, /* orderNum */
	freightType NUMBER NOT NULL, /* freightType */
	freightQuant NUMBER NOT NULL, /* freightQuant */
	freightDetail VARCHAR2(500), /* freightDetail */
	freightPrice NUMBER NOT NULL /* freightPrice */
);

COMMENT ON TABLE freightInfo IS '화물';

COMMENT ON COLUMN freightInfo.freightNum IS 'freightNum';

COMMENT ON COLUMN freightInfo.orderNum IS 'orderNum';

COMMENT ON COLUMN freightInfo.freightType IS 'freightType';

COMMENT ON COLUMN freightInfo.freightQuant IS 'freightQuant';

COMMENT ON COLUMN freightInfo.freightDetail IS 'freightDetail';

COMMENT ON COLUMN freightInfo.freightPrice IS 'freightPrice';

CREATE UNIQUE INDEX PK_freightInfo
	ON freightInfo (
		freightNum ASC
	);

ALTER TABLE freightInfo
	ADD
		CONSTRAINT PK_freightInfo
		PRIMARY KEY (
			freightNum
		);

ALTER TABLE freightinfo
	ADD
		CONSTRAINT FK_priceinfo_TO_freightinfo
		FOREIGN KEY (
			freighttype
		)
		REFERENCES priceinfo (
			freighttype
		);


ALTER TABLE callinfo
	ADD
		CONSTRAINT FK_qpinfo_TO_callinfo
		FOREIGN KEY (
			qpId
		)
		REFERENCES qpinfo (
			qpId
		);

ALTER TABLE callinfo
	ADD
		CONSTRAINT FK_csinfo_TO_callinfo
		FOREIGN KEY (
			csId
		)
		REFERENCES csinfo (
			csId
		);

ALTER TABLE chatinfo
	ADD
		CONSTRAINT FK_csinfo_TO_chatinfo
		FOREIGN KEY (
			csId
		)
		REFERENCES csinfo (
			csId
		);

ALTER TABLE favoriteInfo
	ADD
		CONSTRAINT FK_csinfo_TO_FavoriteInfo
		FOREIGN KEY (
			csId
		)
		REFERENCES csinfo (
			csId
		);

ALTER TABLE orderInfo
	ADD
		CONSTRAINT FK_callinfo_TO_orderInfo
		FOREIGN KEY (
			callNum
		)
		REFERENCES callinfo (
			callNum
		);

ALTER TABLE QPPosition
	ADD
		CONSTRAINT FK_qpinfo_TO_QPPosition
		FOREIGN KEY (
			qpId
		)
		REFERENCES qpinfo (
			qpId
		);

ALTER TABLE QPScore
	ADD
		CONSTRAINT FK_callinfo_TO_QPScore
		FOREIGN KEY (
			callNum
		)
		REFERENCES callinfo (
			callNum
		);

ALTER TABLE QPScore
	ADD
		CONSTRAINT FK_orderInfo_TO_QPScore
		FOREIGN KEY (
			orderNum
		)
		REFERENCES orderInfo (
			orderNum
		);

ALTER TABLE QPScore
	ADD
		CONSTRAINT FK_qpinfo_TO_QPScore
		FOREIGN KEY (
			qpId
		)
		REFERENCES qpinfo (
			qpId
		);

ALTER TABLE freightInfo
	ADD
		CONSTRAINT FK_orderInfo_TO_freightInfo
		FOREIGN KEY (
			orderNum
		)
		REFERENCES orderInfo (
			orderNum
		);
        
        
INSERT INTO CSINFO VALUES('admin', 'admin', '관리자', '000-0000-0000', 0, null, null, null, 99999999, -1);
INSERT INTO PRICEINFO VALUES(1, '서류', 5, 500);
INSERT INTO PRICEINFO VALUES(2, '작은상자', 3, 1000);
INSERT INTO PRICEINFO VALUES(3, '중간상자', 2, 1500);
INSERT INTO PRICEINFO VALUES(4, '큰 상자', 1,500);
INSERT INTO PRICEINFO VALUES(5, '음식물',0, 2000);

COMMIT;