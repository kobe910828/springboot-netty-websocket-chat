package netty.objEncoderDecoder;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2019/3/7 15:06
 */
@Data
public class SubscribeReq implements Serializable{
    private static final long serialVersionUID = 5731834761471962893L;

    /** 订购编号 **/
    private Integer subReqId;
    /** 用户名 **/
    private String userName;
    /** 订购的产品名称 **/
    private String productName;
    /** 订购者电话号码 **/
    private String phoneNumber;
    /** 订购者家庭住址 **/
    private String address;

}
