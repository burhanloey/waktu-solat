package com.burhanloey.waktusolat.modules.esolat;

import com.burhanloey.waktusolat.R;

public final class ESolat {
    public final static String URL = "https://www.burhanloey.com/";

    public final static String[][] DISTRICT_CODES = {
            { "JHR01", "JHR02", "JHR03", "JHR04" },
            { "KDH01", "KDH02", "KDH03", "KDH04", "KDH05", "KDH06", "KDH07" },
            { "KTN01", "KTN03" },
            { "MLK01" },
            { "NGS01", "NGS02" },
            { "PHG01", "PHG02", "PHG03", "PHG04", "PHG05", "PHG06" },
            { "PLS01" },
            { "PNG01" },
            { "PRK01", "PRK02", "PRK03", "PRK04", "PRK05", "PRK06", "PRK07" },
            { "SBH01", "SBH02", "SBH03", "SBH04", "SBH05", "SBH06", "SBH07", "SBH08", "SBH09" },
            { "SGR01", "SGR02", "SGR03" },
            { "SWK01", "SWK02", "SWK03", "SWK04", "SWK05", "SWK06", "SWK07", "SWK08", "SWK09" },
            { "TRG01", "TRG02", "TRG03", "TRG04" },
            { "WLY01", "WLY02" }
    };

    public final static int[] DISTRICT_ARRAYS = {
            R.array.district_Johor,
            R.array.district_Kedah,
            R.array.district_Kelantan,
            R.array.district_Melaka,
            R.array.district_Negeri_Sembilan,
            R.array.district_Pahang,
            R.array.district_Perlis,
            R.array.district_Pulau_Pinang,
            R.array.district_Perak,
            R.array.district_Sabah,
            R.array.district_Selangor,
            R.array.district_Sarawak,
            R.array.district_Terengganu,
            R.array.district_Wilayah_Persekutuan
    };

    /**
     * Get district code from the position of spinner.
     *
     * @param statePosition Spinner position for state
     * @param districtPosition Spinner position for district
     * @return District code
     */
    public static String getDistrictCode(int statePosition, int districtPosition) {
        return DISTRICT_CODES[statePosition][districtPosition];
    }

    public static int getDistrictArray(int statePosition) {
        return DISTRICT_ARRAYS[statePosition];
    }
}
