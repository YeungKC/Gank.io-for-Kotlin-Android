package com.yeungkc.gank.io.model.repo.source.memory



abstract class  BaseMemorySource<T>: IMemorySource<T>{
    override var mData:T? = null
}