package com.sparta.travelshooting.user.entity;

public enum RegionEnum {
    서울("SEOUL"), //서울
    경기("GYEONGGIDO"), //경기
    인천("INCHEON"), //인천
    충남("CHUNGCHEONGNAMDO"), //충남
    충북("CHUNGCHEONGBUKDO"), //충북
    대전("DAEJEON"), //대전
    강원("GANGWONDO"), //강원
    경북("GYEONGSANGBUKDO"), //경북
    대구("DAEGU"), //대구
    울산("ULSAN"), //울산
    경남("GYEONGSANGNAMDO"), //경남
    부산("BUSAN"), //부산
    전북("JEOLLABUKDO"), // 전북
    전남("JEOLLANAMDO"), // 전남
    광주("GWANGJU"), //광주
    제주("JEJU"); //제주

    private final String engName;

    RegionEnum(String engName) {
        this.engName = engName;
    }

    //Getter
    public String getEngName() {
        return engName;
    }
}
