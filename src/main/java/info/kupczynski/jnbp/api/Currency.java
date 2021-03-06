package info.kupczynski.jnbp.api;

import static java.util.Objects.requireNonNull;

/**
 * Currency represented by its code and the table that its rates are stored in
 *
 * @see <a href="https://www.nbp.pl/Kursy/KursyA.html">Table A</a>
 * @see <a href="https://www.nbp.pl/Kursy/KursyB.html">Table B</a>
 * @see <a href="https://www.nbp.pl/Kursy/KursyC.html">Table C</a>
 */
public enum Currency {

    THB_A(CurrencyTable.A, "THB"),
    USD_A(CurrencyTable.A, "USD"),
    AUD_A(CurrencyTable.A, "AUD"),
    HKD_A(CurrencyTable.A, "HKD"),
    CAD_A(CurrencyTable.A, "CAD"),
    NZD_A(CurrencyTable.A, "NZD"),
    SGD_A(CurrencyTable.A, "SGD"),
    EUR_A(CurrencyTable.A, "EUR"),
    HUF_A(CurrencyTable.A, "HUF"),
    CHF_A(CurrencyTable.A, "CHF"),
    GBP_A(CurrencyTable.A, "GBP"),
    UAH_A(CurrencyTable.A, "UAH"),
    JPY_A(CurrencyTable.A, "JPY"),
    CZK_A(CurrencyTable.A, "CZK"),
    DKK_A(CurrencyTable.A, "DKK"),
    ISK_A(CurrencyTable.A, "ISK"),
    NOK_A(CurrencyTable.A, "NOK"),
    SEK_A(CurrencyTable.A, "SEK"),
    HRK_A(CurrencyTable.A, "HRK"),
    RON_A(CurrencyTable.A, "RON"),
    BGN_A(CurrencyTable.A, "BGN"),
    TRY_A(CurrencyTable.A, "TRY"),
    ILS_A(CurrencyTable.A, "ILS"),
    CLP_A(CurrencyTable.A, "CLP"),
    PHP_A(CurrencyTable.A, "PHP"),
    MXN_A(CurrencyTable.A, "MXN"),
    ZAR_A(CurrencyTable.A, "ZAR"),
    BRL_A(CurrencyTable.A, "BRL"),
    MYR_A(CurrencyTable.A, "MYR"),
    RUB_A(CurrencyTable.A, "RUB"),
    IDR_A(CurrencyTable.A, "IDR"),
    INR_A(CurrencyTable.A, "INR"),
    KRW_A(CurrencyTable.A, "KRW"),
    CNY_A(CurrencyTable.A, "CNY"),
    XDR_A(CurrencyTable.A, "XDR"),
    LTL_A(CurrencyTable.A, "LTL"),
    LVL_A(CurrencyTable.A, "LVL"),
    EEK_A(CurrencyTable.A, "EEK"),
    SIT_A(CurrencyTable.A, "SIT"),
    MTL_A(CurrencyTable.A, "MTL"),
    CYP_A(CurrencyTable.A, "CYP"),
    SKK_A(CurrencyTable.A, "SKK"),
    IEP_A(CurrencyTable.A, "IEP"),
    DEM_A(CurrencyTable.A, "DEM"),
    NLG_A(CurrencyTable.A, "NLG"),
    FIM_A(CurrencyTable.A, "FIM"),
    FRF_A(CurrencyTable.A, "FRF"),
    MCF_A(CurrencyTable.A, "MCF"),
    ATS_A(CurrencyTable.A, "ATS"),
    BEF_A(CurrencyTable.A, "BEF"),
    LUF_A(CurrencyTable.A, "LUF"),
    ESP_A(CurrencyTable.A, "ESP"),
    PTE_A(CurrencyTable.A, "PTE"),
    GRD_A(CurrencyTable.A, "GRD"),
    SML_A(CurrencyTable.A, "SML"),
    VAL_A(CurrencyTable.A, "VAL"),
    ITL_A(CurrencyTable.A, "ITL"),

    AFN_B(CurrencyTable.B, "AFN"),
    MGA_B(CurrencyTable.B, "MGA"),
    PAB_B(CurrencyTable.B, "PAB"),
    ETB_B(CurrencyTable.B, "ETB"),
    VEF_B(CurrencyTable.B, "VEF"),
    BOB_B(CurrencyTable.B, "BOB"),
    CRC_B(CurrencyTable.B, "CRC"),
    SVC_B(CurrencyTable.B, "SVC"),
    NIO_B(CurrencyTable.B, "NIO"),
    GMD_B(CurrencyTable.B, "GMD"),
    MKD_B(CurrencyTable.B, "MKD"),
    DZD_B(CurrencyTable.B, "DZD"),
    BHD_B(CurrencyTable.B, "BHD"),
    IQD_B(CurrencyTable.B, "IQD"),
    JOD_B(CurrencyTable.B, "JOD"),
    KWD_B(CurrencyTable.B, "KWD"),
    LYD_B(CurrencyTable.B, "LYD"),
    RSD_B(CurrencyTable.B, "RSD"),
    TND_B(CurrencyTable.B, "TND"),
    MAD_B(CurrencyTable.B, "MAD"),
    AED_B(CurrencyTable.B, "AED"),
    STD_B(CurrencyTable.B, "STD"),
    BSD_B(CurrencyTable.B, "BSD"),
    BBD_B(CurrencyTable.B, "BBD"),
    BZD_B(CurrencyTable.B, "BZD"),
    BND_B(CurrencyTable.B, "BND"),
    FJD_B(CurrencyTable.B, "FJD"),
    GYD_B(CurrencyTable.B, "GYD"),
    JMD_B(CurrencyTable.B, "JMD"),
    LRD_B(CurrencyTable.B, "LRD"),
    NAD_B(CurrencyTable.B, "NAD"),
    SRD_B(CurrencyTable.B, "SRD"),
    TTD_B(CurrencyTable.B, "TTD"),
    XCD_B(CurrencyTable.B, "XCD"),
    SBD_B(CurrencyTable.B, "SBD"),
    VND_B(CurrencyTable.B, "VND"),
    AMD_B(CurrencyTable.B, "AMD"),
    CVE_B(CurrencyTable.B, "CVE"),
    AWG_B(CurrencyTable.B, "AWG"),
    BIF_B(CurrencyTable.B, "BIF"),
    XOF_B(CurrencyTable.B, "XOF"),
    XAF_B(CurrencyTable.B, "XAF"),
    XPF_B(CurrencyTable.B, "XPF"),
    DJF_B(CurrencyTable.B, "DJF"),
    GNF_B(CurrencyTable.B, "GNF"),
    KMF_B(CurrencyTable.B, "KMF"),
    CDF_B(CurrencyTable.B, "CDF"),
    RWF_B(CurrencyTable.B, "RWF"),
    EGP_B(CurrencyTable.B, "EGP"),
    GIP_B(CurrencyTable.B, "GIP"),
    LBP_B(CurrencyTable.B, "LBP"),
    SDG_B(CurrencyTable.B, "SDG"),
    SYP_B(CurrencyTable.B, "SYP"),
    GHS_B(CurrencyTable.B, "GHS"),
    HTG_B(CurrencyTable.B, "HTG"),
    PYG_B(CurrencyTable.B, "PYG"),
    ANG_B(CurrencyTable.B, "ANG"),
    PGK_B(CurrencyTable.B, "PGK"),
    LAK_B(CurrencyTable.B, "LAK"),
    MWK_B(CurrencyTable.B, "MWK"),
    ZMW_B(CurrencyTable.B, "ZMW"),
    AOA_B(CurrencyTable.B, "AOA"),
    MMK_B(CurrencyTable.B, "MMK"),
    GEL_B(CurrencyTable.B, "GEL"),
    MDL_B(CurrencyTable.B, "MDL"),
    ALL_B(CurrencyTable.B, "ALL"),
    HNL_B(CurrencyTable.B, "HNL"),
    SLL_B(CurrencyTable.B, "SLL"),
    SZL_B(CurrencyTable.B, "SZL"),
    LSL_B(CurrencyTable.B, "LSL"),
    AZN_B(CurrencyTable.B, "AZN"),
    MZN_B(CurrencyTable.B, "MZN"),
    NGN_B(CurrencyTable.B, "NGN"),
    ERN_B(CurrencyTable.B, "ERN"),
    TWD_B(CurrencyTable.B, "TWD"),
    PEN_B(CurrencyTable.B, "PEN"),
    MRO_B(CurrencyTable.B, "MRO"),
    TOP_B(CurrencyTable.B, "TOP"),
    MOP_B(CurrencyTable.B, "MOP"),
    ARS_B(CurrencyTable.B, "ARS"),
    DOP_B(CurrencyTable.B, "DOP"),
    COP_B(CurrencyTable.B, "COP"),
    UYU_B(CurrencyTable.B, "UYU"),
    BWP_B(CurrencyTable.B, "BWP"),
    GTQ_B(CurrencyTable.B, "GTQ"),
    IRR_B(CurrencyTable.B, "IRR"),
    YER_B(CurrencyTable.B, "YER"),
    QAR_B(CurrencyTable.B, "QAR"),
    OMR_B(CurrencyTable.B, "OMR"),
    SAR_B(CurrencyTable.B, "SAR"),
    KHR_B(CurrencyTable.B, "KHR"),
    BYN_B(CurrencyTable.B, "BYN"),
    LKR_B(CurrencyTable.B, "LKR"),
    MVR_B(CurrencyTable.B, "MVR"),
    MUR_B(CurrencyTable.B, "MUR"),
    NPR_B(CurrencyTable.B, "NPR"),
    PKR_B(CurrencyTable.B, "PKR"),
    SCR_B(CurrencyTable.B, "SCR"),
    KGS_B(CurrencyTable.B, "KGS"),
    TJS_B(CurrencyTable.B, "TJS"),
    UZS_B(CurrencyTable.B, "UZS"),
    KES_B(CurrencyTable.B, "KES"),
    SOS_B(CurrencyTable.B, "SOS"),
    TZS_B(CurrencyTable.B, "TZS"),
    UGX_B(CurrencyTable.B, "UGX"),
    BDT_B(CurrencyTable.B, "BDT"),
    WST_B(CurrencyTable.B, "WST"),
    KZT_B(CurrencyTable.B, "KZT"),
    MNT_B(CurrencyTable.B, "MNT"),
    VUV_B(CurrencyTable.B, "VUV"),
    BAM_B(CurrencyTable.B, "BAM"),

    USD_C(CurrencyTable.C, "USD"),
    AUD_C(CurrencyTable.C, "AUD"),
    CAD_C(CurrencyTable.C, "CAD"),
    EUR_C(CurrencyTable.C, "EUR"),
    HUF_C(CurrencyTable.C, "HUF"),
    CHF_C(CurrencyTable.C, "CHF"),
    GBP_C(CurrencyTable.C, "GBP"),
    JPY_C(CurrencyTable.C, "JPY"),
    CZK_C(CurrencyTable.C, "CZK"),
    DKK_C(CurrencyTable.C, "DKK"),
    NOK_C(CurrencyTable.C, "NOK"),
    SEK_C(CurrencyTable.C, "SEK"),
    XDR_C(CurrencyTable.C, "XDR");

    public final CurrencyTable table;
    public final String code;

    Currency(CurrencyTable table, String code) {
        this.table = table;
        this.code = code;
    }

    public static Currency valueOf(CurrencyTable table, String code) {
        requireNonNull(table);
        requireNonNull(code);
        for (Currency c : Currency.values()) {
            if (c.table == table && c.code.equals(code))
                return c;
        }
        throw new IllegalArgumentException(String.format("Currency not found table %s -> %s", table, code));
    }
}
