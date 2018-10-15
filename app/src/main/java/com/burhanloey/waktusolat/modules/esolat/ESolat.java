package com.burhanloey.waktusolat.modules.esolat;

public final class ESolat {
    public final static String URL = "https://www.e-solat.gov.my/";

    public final static String[] DISTRICT_CODES = { "JHR01", "JHR02", "JHR03", "JHR04", "KDH01",
            "KDH02", "KDH03", "KDH04", "KDH05", "KDH06", "KDH07", "KTN01", "KTN03", "MLK01",
            "NGS01", "NGS02", "PHG01", "PHG02", "PHG03", "PHG04", "PHG05", "PHG06", "PLS01",
            "PNG01", "PRK01", "PRK02", "PRK03", "PRK04", "PRK05", "PRK06", "PRK07", "SBH01",
            "SBH02", "SBH03", "SBH04", "SBH05", "SBH06", "SBH07", "SBH08", "SBH09", "SGR01",
            "SGR02", "SGR03", "SWK01", "SWK02", "SWK03", "SWK04", "SWK05", "SWK06", "SWK07",
            "SWK08", "SWK09", "TRG01", "TRG02", "TRG03", "TRG04", "WLY01", "WLY02" };

    /**
     * Get district code from the position of spinner.
     *
     * @param position Spinner position
     * @return District code
     */
    public static String getDistrictCode(int position) {
        return DISTRICT_CODES[position];
    }
}