//package com.example.choijinjoo.wingdroid.source;
//
//import com.example.choijinjoo.wingdroid.model.SearchHistory;
//
//import io.reactivex.Observable;
//import io.realm.Realm;
//import io.realm.RealmConfiguration;
//import io.realm.RealmResults;
//import io.realm.Sort;
//
///**
// * Created by choijinjoo on 2017. 8. 10..
// */
//
//public class SearchHistoryLocalSource {
//    RealmConfiguration config;
//
//    public SearchHistoryLocalSource() {
//        this.config = createRealmConfiguration();
//    }
//
//    public Observable<RealmResults<SearchHistory>> getSearchHistories() {
//        Realm realm = Realm.getInstance(config);
//        RealmResults realmResults =  realm.where(SearchHistory.class)
//                .findAllSorted("insertedAt", Sort.DESCENDING);
//        return Observable.just(realmResults);
//    }
//
//    public void saveSearchHistory(SearchHistory searchHistory) {
//        Realm realm = Realm.getInstance(config);
//        realm.executeTransactionAsync(realm1 -> {
//            realm1.insertOrUpdate(searchHistory);
//        });
//        realm.close();
//    }
//
//    public void deleteSearchHistory(SearchHistory searchHistory) {
//        Realm realm = Realm.getInstance(config);
//        realm.executeTransactionAsync(realm1 -> {
//            searchHistory.deleteFromRealm();
//        });
//        realm.close();
//    }
//
//
//    private RealmConfiguration createRealmConfiguration() {
//        return new RealmConfiguration.Builder()
//                .name("wingdroid.realm")
//                .deleteRealmIfMigrationNeeded()
//                .schemaVersion(1)
//                .build();
//    }
//}
