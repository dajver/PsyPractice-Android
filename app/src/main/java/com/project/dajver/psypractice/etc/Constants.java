package com.project.dajver.psypractice.etc;

/**
 * Created by gleb on 11/7/17.
 */

public class Constants {

    public static final String BASE_URL = "https://psy-practice.com";
    public static final String LINK_PUBLICATIONS = "/publications";
    public static final String LINK_PAGE = "/?PAGEN_1=";
    public static final String LINK_SEARCH_PAGE = "&where=&how=r&PAGEN_1=";
    public static final String LIST_LAST_PUBLICATIONS = BASE_URL + LINK_PUBLICATIONS;

    public static final String LIST_OF_PSYCHO_HEALTH = LIST_LAST_PUBLICATIONS  + "/psikhicheskoe-zdorove/";
    public static final String LIST_OF_TRAUMAS = LIST_LAST_PUBLICATIONS  + "/travmy/";
    public static final String LIST_OF_RELATION = LIST_LAST_PUBLICATIONS  + "/lichnye-otnosheniya/";
    public static final String LIST_OF_OLD_AND_YONG = LIST_LAST_PUBLICATIONS  + "/vzroslye-i-deti/";
    public static final String LIST_OF_DEPENDENCY = LIST_LAST_PUBLICATIONS  + "/zavisimosti/";
    public static final String LIST_OF_WORK_AND_SOCIETY = LIST_LAST_PUBLICATIONS  + "/rabota-obshchestvo/";
    public static final String LIST_OF_BUSINESS = LIST_LAST_PUBLICATIONS  + "/biznes/";
    public static final String LIST_OF_OTHER = LIST_LAST_PUBLICATIONS  + "/prochee/";
    public static final String LIST_OF_ON_RECEPTION = LIST_LAST_PUBLICATIONS  + "/na-prieme/";

    public static final String LIST_VIDEOS_LINK = BASE_URL + "/useful/video/";
    public static final String LIST_EVENTS_LINK = BASE_URL + "/activity/";
    public static final String LIST_SPECIALISTS_LINK = BASE_URL + "/therapists-find/";
    public static final String LIST_SEARCH_LINK = BASE_URL + "/search/?q=";

    public static final String INTENT_LINK = "link";
    public static final String INTENT_TITLE = "title";

    public static final int DRAWER_PUBLICATIONS = 0;
    public static final int DRAWER_PSYCHO_HEALTH = 1;
    public static final int DRAWER_TRAUMA = 2;
    public static final int DRAWER_RELATION = 3;
    public static final int DRAWER_OLD_AND_YONG = 4;
    public static final int DRAWER_DEPENDENCY = 5;
    public static final int DRAWER_WORK_AND_SOCIETY = 6;
    public static final int DRAWER_BUSINESS = 7;
    public static final int DRAWER_OTHER = 8;
    public static final int DRAWER_ON_RECEPTION = 9;

    public static final int TAB_HOME = 0;
    public static final int TAB_VIDEOS = 1;
    public static final int TAB_SEARCH = 2;
    public static final int TAB_FAVORITE = 3;
}
