package com.qrqy.dah.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class UploadFileQO {

	@ApiModelProperty(value = "文件", example = "")
	@NotNull
	private MultipartFile file;
}
