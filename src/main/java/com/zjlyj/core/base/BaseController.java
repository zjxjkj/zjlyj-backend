package com.zjlyj.core.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BaseController<T extends BaseDTO> {

    @Autowired
    protected OrikaBeanMapper beanMapper;

    // region set success

    public BaseResponse<T> setResultSuccess(String msg, T data) {
        return setResult(BaseResultCode.SUCCESS, msg, data);
    }

    public BaseResponse<T> setResultSuccess(String msg, BaseResultAttachCode attCode, T data) {
        return setResult(BaseResultCode.SUCCESS, attCode, msg, null);
    }

    public BaseResponse<List<T>> setResultSuccess(String msg, List<T> data) {
        return setResultList(BaseResultCode.SUCCESS, msg, data);
    }

    public BaseResponse<T> setResultSuccess(String msg) {
        return setResult(BaseResultCode.SUCCESS, msg, null);
    }


    public BaseResponse<DefaultPageResponseDTO<T>> setResultSuccess(String msg, IPage<?> page, Class<T> dtoType) {
        // map page and data
        DefaultPageInfoDTO pageInfo = beanMapper.map(page, DefaultPageInfoDTO.class);
        List<T> lists = beanMapper.mapAsList(page.getRecords(), dtoType);
        pageInfo.setCurrentPage(page.getCurrent());
        pageInfo.setPageSize(page.getSize());
        pageInfo.setTotalCount(page.getTotal());
        DefaultPageResponseDTO<T> defaultPageResponseDTO = new DefaultPageResponseDTO<>();
        defaultPageResponseDTO.setPage(pageInfo);
        defaultPageResponseDTO.setLists(lists);

        return ControllerUtil.setResultSuccess(msg, defaultPageResponseDTO);
    }

    // endregion

    // region set fault

    public BaseResponse<T> setResultFault(String msg, T data) {
        return setResult(BaseResultCode.FAULT, msg, data);
    }

    public BaseResponse<T> setResultFault(String msg) {
        return setResult(BaseResultCode.FAULT, msg, null);
    }

    // endregion

    // region base set

    private BaseResponse<T> setResult(String code, String msg, T data) {
        return new BaseResponse<T>(new BaseResult(code, msg), data);
    }

    private BaseResponse<List<T>> setResultList(String code, String msg, List<T> data) {
        return new BaseResponse<List<T>>(new BaseResult(code, msg), data);
    }

    private BaseResponse<T> setResult(String code, BaseResultAttachCode attCode, String msg, T data) {
        return new BaseResponse<T>(new BaseResult(code, msg, attCode), data);
    }

    // endregion
}