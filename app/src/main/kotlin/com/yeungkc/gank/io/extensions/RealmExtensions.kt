package com.yeungkc.gank.io.extensions

import io.realm.Realm
import io.realm.RealmObject

/**
 * Created by YeungKC on 16/2/26.
 *
 * @项目名: kc
 * @包名: gank.io.kc.extensions
 * @作者: YeungKC
 *
 * @描述：TODO
 */
fun<T> T.saveToRealm(realmObject: RealmObject){
    Realm.getDefaultInstance().apply {
        beginTransaction()
        copyToRealmOrUpdate(realmObject)
        commitTransaction()
    }
}