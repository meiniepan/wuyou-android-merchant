package com.wuyou.merchant;

import android.os.Environment;

/**
 * Created by Administrator on 2018\1\26 0026.
 */

public class Constant {
    public static final String VOTE_ROW_BEAN = "vote_row_bean";
    public static final String HAS_VOTE = "has_vote";
    public static final String SP_IPFS_URL = "sp_ipfs_url";
    public static final String FOR_UPDATE = "update";
    //切环境时候注意融云ID变更
    public static String CHAIN_URL = "http://mainnet.eosoasis.io:8889/";
    public static String IPFS_URL = "mainnet.eosoasis.io";
    public static String HTTP_IPFS_URL = "http://mainnet.eosoasis.io:5001/api/v0/cat?arg=";
    public static String DEV_HTTP_IPFS_URL = "http://192.168.1.186:5001/api/v0/cat?arg=";
    public static String BASE_URL = "http://api.ulaidao.cn/merchants/v1/";
    public static String BASE_URL_2 = "http://192.168.1.156:5000";

    public static String DEV_BASE_URL = "https://develop.api.ulaidao.cn/merchants/v1/";
    public static String STAGE_BASE_URL = "https://stage.api.ulaidao.cn/merchants/v1/";
    public static String ONLINE_BASE_URL = "https://api.ulaidao.cn/merchants/v1/";

    public static String DEV_CHAIN_URL = "http://192.168.1.186:8888/";
    public static String STAGE_CHAIN_URL = "http://192.168.1.186:8888/";
    public static String ONLINE_CHAIN_URL = "http://mainnet.eosoasis.io:8889/";

    public static String DEV_IPFS_URL = "192.168.1.186";
    public static String STAGE_IPFS_URL = "192.168.1.186";
    public static String ONLINE_IPFS_URL = "mainnet.eosoasis.io";


    public static final String SP_CHAIN_URL = "sp_chain_url";
    public static final String ACTIVITY_CREATE_VOTE = "votevotevot1";
    public static final String EOSIO_SYSTEM_ACCOUNT = "signupcoming";
    public static final String EOSIO_DAILAY_REWARDS = "dailyrewards";
    public static final String ACTIVITY_DAILAY_REWARDS = "activity1111";
    public static final String EOSIO_TOKEN_CONTRACT = "eosio.token";
    public static final int TX_EXPIRATION_IN_MILSEC = 30000;

    public static final String ABOUT_US_URL = "https://ulaidao.ulaidao.cn/apphtml/about-us.html";
    public static final String SP_BASE_URL = "sp_base_url";
    public static final String INPUT_PHONE_FLAG = "input_phone_sign";
    public static final String ORDER_ID = "order_id";
    public static final String PHONE = "phone";
    public static final String CAPTCHA = "captcha";
    public static final String DB_FORM_NAME = "form_name";
    public static final String CONTRACT_ID = "contract_id";
    public static final String CONTRACT_ENTITY = "contract_entity";
    public static final String IMAGE1_URL = "image1_url";
    public static final String FUND_ID = "fund_id";
    public static final String WALLET_INFO_ENTITY = "wallet_info_entity";
    public static final String CREDIT_SCORE = "credit_score";
    public static final String CONTRACT_FROM = "contract_from";
    public static final String TRANSACTION_ENTITY = "transaction_entity";
    public static final String FUND = "fund";
    public static final String SHOP_ID = "shop_id";
    public static final String SERVE_BEAN = "serve_bean";
    public static final String PAY_TYPE = "pay_type";
    public static final String CONTRACT = "contract";
    public static final String SIGN_NUMBER = "sign_number";
    public static final String FUND_STATUS = "fund_status";
    public static final String FIRST_OPEN = "first_open";
    public static final String MAIN_ACTIVITY_FROM_WHERE = "main_activity_from_where";
    public static final String MAIN_ACTIVITY_FROM_CREATE_CONTRACT = "main_activity_from_create_contract";
    public static final String MAIN_ACTIVITY_FROM_APPLY_FUND = "main_activity_from_apply_fund";
    public static final String MAIN_ACTIVITY_FROM_VOUCHER = "main_activity_from_voucher";
    public static final String WX_ID = "wxb60eda0adace922f";
    public static final String WX_SECRET = "f69fe20fe89637098fe08474ce8b90bd";
    public static final String AUTH_IMG_PATH_1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/wuyou_tmp.jpg";
    public static final int REQUEST_NICK = 777;
    public static final int REQUEST_PHONE = 888;
    public static final String WEB_INTENT = "web_intent";
    public static final String WEB_TYPE = "web_type";
    public static final String WEB_URL = "web_url";
    public static final String AUTH_IMG_PATH_2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/wuyou_tmp_2.jpg";
    public static final String COMPANY_INFO = "company_info";
    public static String IMAGE1_URL_2 = "image1_url_2";
    public static String HELP_SERVE_AGENT_ID = "181385";
    public static final String IMPORT_ACCOUNT = "import_account";
    public static final String BACKUP_FROM_CREATE = "back_from_create";
    public static final int COUNT_DOWN = 119;
    public static final String CAPTCHA_NEW_ACCOUNT = "new_account";
    public static int GET_CAPTCHA_FAIL = 600;
    public static String UN_FINISH = "un_finish";


    public static class IntentRequestCode {
        public static final int CHOOSE_SERVICE_CATEGORY = 200;
        public static final int CHOOSE_PAY_TYPE = 201;
        public static final int REQUEST_CODE_CHOOSE_IMAGE = 202;
        public static final int REQUEST_CODE_CHOOSE_IMAGE_2 = 203;
        public static final int REQUEST_UPDATE_COMPANY_INFO = 204;
    }
}
