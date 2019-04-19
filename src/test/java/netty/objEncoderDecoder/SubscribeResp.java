package netty.objEncoderDecoder;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2019/3/7 15:17
 */
@Data
public class SubscribeResp implements Serializable{
    private static final long serialVersionUID = 214517927558943086L;

    /** 订购编号 **/
    private int subReqId;
    /** 订购结果: 0表示成功 **/
    private Integer respCode;
    /** 详细描述信息 **/
    private String desc;

}
