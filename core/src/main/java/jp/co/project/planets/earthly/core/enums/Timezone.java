package jp.co.project.planets.earthly.core.enums;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public enum Timezone {

    //  南アフリカ標準時
    AFRICA_JOHANNESBURG("Africa/Johannesburg"),
    // 西アフリカ時間
    AFRICA_LAGOS("Africa/Lagos"),
    // 西アフリカ時間
    AFRICA_WINDHOEK("Africa/Windhoek"),
    // ハワイ - アリューシャン標準時
    AMERICA_ADAK("America/Adak"),
    // アラスカ標準時
    AMERICA_ANCHORAGE("America/Anchorage"),
    // アルゼンチン時間
    AMERICA_ARGENTINA_BUENOS_AIRES("America/Argentina/Buenos_Aires"),
    // コロンビア時間
    AMERICA_CARACAS("America/Caracas"),
    // ベネズエラ時間
    AMERICA_BOGOTA("America/Bogota"),
    // 中部標準時
    AMERICA_CHICAGO("America/Chicago"),
    // 山地標準時
    AMERICA_DENVER("America/Denver"),
    // 西グリーンランド時間
    AMERICA_GODTHAB("America/Godthab"),
    // 中部標準時
    AMERICA_GUATEMALA("America/Guatemala"),
    // 大西洋標準時
    AMERICA_HALIFAX("America/Halifax"),
    // 太平洋標準時
    AMERICA_LOS_ANGELES("America/Los_Angeles"),
    // ウルグアイ時間
    AMERICA_MONTEVIDEO("America/Montevideo"),
    // 東部標準時
    AMERICA_NEW_YORK("America/New_York"),
    // フェルナンド・デ・ノローニャ時間
    AMERICA_NORONHA("America/Noronha"),
    // 山地標準時
    AMERICA_PHOENIX("America/Phoenix"),
    // チリ時間
    AMERICA_SANTIAGO("America/Santiago"),
    // 大西洋標準時
    AMERICA_SANTO_DOMINGO("America/Santo_Domingo"),
    // ニューファンドランド標準時
    AMERICA_ST_JOHNS("America/St_Johns"),
    // アラビア標準時
    ASIA_BAGHDAD("Asia/Baghdad"),
    // アゼルバイジャン時間
    ASIA_BAKU("Asia/Baku"),
    // 東ヨーロッパ時間
    ASIA_BEIRUT("Asia/Beirut"),
    // バングラデシュ時間
    ASIA_DHAKA("Asia/Dhaka"),
    // 湾岸標準時
    ASIA_DUBAI("Asia/Dubai"),
    // イルクーツク時間
    ASIA_IRKUTSK("Asia/Irkutsk"),
    // 西インドネシア時間
    ASIA_JAKARTA("Asia/Jakarta"),
    // アフガニスタン時間
    ASIA_KABUL("Asia/Kabul"),
    // ペトロパブロフスクカムチャツキー時間
    ASIA_KAMCHATKA("Asia/Kamchatka"),
    // パキスタン時間
    ASIA_KARACHI("Asia/Karachi"),
    // ネパール時間
    ASIA_KATHMANDU("Asia/Kathmandu"),
    // インド標準時
    ASIA_KOLKATA("Asia/Kolkata"),
    // クラスノヤルスク時間
    ASIA_KRASNOYARSK("Asia/Krasnoyarsk"),
    // オムスク時間
    ASIA_OMSK("Asia/Omsk"),
    // ミャンマー時間
    ASIA_RANGOON("Asia/Rangoon"),
    // 中国標準時
    ASIA_SHANGHAI("Asia/Shanghai"),
    // イラン標準時
    ASIA_TEHRAN("Asia/Tehran"),
    // 日本標準時
    ASIA_TOKYO("Asia/Tokyo"),
    // ウラジオストク時間
    ASIA_VLADIVOSTOK("Asia/Yakutsk"),
    // ヤクーツク時間
    ASIA_YAKUTSK("Asia/Vladivostok"),
    // エカテリンブルグ時間
    ASIA_YEKATERINBURG("Asia/Yekaterinburg"),
    // アゾレス時間
    ATLANTIC_AZORES("Atlantic/Azores"),
    // カーボベルデ時間
    ATLANTIC_CAPE_VERDE("Atlantic/Cape_Verde"),
    // 中部標準時 （南オーストラリア）
    AUSTRALIA_ADELAIDE("Australia/Adelaide"),
    // 東部標準時 （クイーンズランド）	
    AUSTRALIA_BRISBANE("Australia/Brisbane"),
    // 中部標準時 （ノーザンテリトリー）
    AUSTRALIA_DARWIN("Australia/Darwin"),
    // 中西部標準時 （オーストラリア）
    AUSTRALIA_EUCLA("Australia/Eucla"),
    // ロードハウ島標準時
    AUSTRALIA_LORD_HOWE("Australia/Lord_Howe"),
    // 東部標準時 （ニューサウスウェールズ）
    AUSTRALIA_SYDNEY("Australia/Sydney"),
    // GMT-12:00
    ETC_GMT("Etc/GMT"),
    // 中部ヨーロッパ時間
    EUROPE_BERLIN("Europe/Berlin"),
    // グリニッジ標準時
    EUROPE_LONDON("Europe/London"),
    // モスクワ標準時
    EUROPE_MOSCOW("Europe/Moscow"),
    // 西サモア時間
    PACIFIC_APIA("Pacific/Apia"),
    // ニュージーランド標準時
    PACIFIC_AUCKLAND("Pacific/Auckland"),
    // チャタム標準時
    PACIFIC_CHATHAM("Pacific/Chatham"),
    // イースター島時間
    PACIFIC_EASTER("Pacific/Easter"),
    // ガンビア時間
    PACIFIC_GAMBIER("Pacific/Gambier"),
    // ハワイ標準時
    PACIFIC_HONOLULU("Pacific/Honolulu"),
    // ライン諸島時間
    PACIFIC_KIRITIMATI("Pacific/Kiritimati"),
    // マーシャル島時間
    PACIFIC_MAJURO("Pacific/Majuro"),
    // マルケサス時間
    PACIFIC_MARQUESAS("Pacific/Marquesas"),
    // ノーフォーク時間
    PACIFIC_NORFOLK("Pacific/Norfolk"),
    // ニューカレドニア時間
    PACIFIC_NOUMEA("Pacific/Noumea"),
    // サモア標準時
    PACIFIC_PAGO_PAGO("Pacific/Pago_Pago"),
    // ピトケルン島標準時
    PACIFIC_PITCAIRN("Pacific/Pitcairn"),
    // トンガ時間
    PACIFIC_TONGATAPU("Pacific/Tongatapu"),
    // 協定世界時
    UTC("UTC");

    private final String id;

    Timezone(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static Timezone of(final String value) {
        return Arrays.stream(Timezone.values()).filter(it -> StringUtils.equals(it.id, value)).findFirst().orElse(null);
    }
}
