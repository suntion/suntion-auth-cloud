package com.suntion.common.lang;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.suntion.common.constants.HttpConstants;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 使用 建造者模式 构造返回对象
 * @author suntion
 * @since 2018-04-19 14:36:41
 *
 */
@JsonInclude(Include.NON_EMPTY)
@Accessors(chain = true)
@Data
public class ResponseEntity implements Serializable{
    private static final long serialVersionUID = 1091032671849867704L;
	
    public static final String CODE = "code";
    public static final String RESULT = "result";
    public static final String MESSAGE = "message";

	private String code;
	private Object result;
	private String message;

	public ResponseEntity() {
		this.code = HttpConstants.CODE_SUCCESS;
	}
	
	public ResponseEntity(String code, Object result) {
		this.code = code;
		this.result = result;
	}
	
	public ResponseEntity(String code, Object result, String message) {
		this.code = code;
		this.result = result;
		this.message = message;
	}


	public static ResponseEntity success() {
		return new ResponseEntity().setCode(HttpConstants.CODE_SUCCESS);
    }
	
	public static ResponseEntity success(Object result) {
		return new ResponseEntity().setCode(HttpConstants.CODE_SUCCESS).setResult(result);
    }
	
	public static ResponseEntity success(Object result, String message) {
		return new ResponseEntity().setCode(HttpConstants.CODE_SUCCESS).setResult(result).setMessage(message);
    }
	
	public static ResponseEntity failed() {
		return new ResponseEntity().setCode(HttpConstants.CODE_FAILED);
    }
	
	public static ResponseEntity failed(Object result) {
		return new ResponseEntity().setCode(HttpConstants.CODE_FAILED).setResult(result);
    }
	
	public static ResponseEntity failed(Object result, String message) {
		return new ResponseEntity().setCode(HttpConstants.CODE_FAILED).setResult(result).setMessage(message);
    }

}
