package com.chiclaim.router.plugin.handler;

public interface IAttentionAnnotation extends IAttention {

    /**
     * 是否是我关心的注解
     *
     * @return boolean
     */
    boolean isAttentionAnnotation(String desc);


}
