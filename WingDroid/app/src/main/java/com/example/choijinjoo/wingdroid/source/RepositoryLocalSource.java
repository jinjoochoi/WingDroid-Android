package com.example.choijinjoo.wingdroid.source;

import com.example.choijinjoo.wingdroid.model.Repository;

import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by choijinjoo on 2017. 8. 10..
 */

public class RepositoryLocalSource {
    RealmConfiguration config;

    public RepositoryLocalSource() {
        this.config = createRealmConfiguration();
    }

    public Observable<RealmResults<Repository>> getSuggestionRepository(String text) {
        Realm realm = Realm.getInstance(config);
        RealmResults realmResults =  realm.where(Repository.class)
                .contains("name", text)
                .or()
                .contains("description", text)
                .findAll();
        return Observable.just(realmResults);

    }

    public void saveRepository(Repository repository) {
        Realm realm = Realm.getInstance(config);
        realm.executeTransactionAsync(realm1 -> {
            realm1.insertOrUpdate(repository);
        });
        realm.close();
    }

    public void saveRepositories(List<Repository> repositories) {
        Realm realm = Realm.getInstance(config);
        realm.executeTransactionAsync(realm1 -> {
            realm1.insertOrUpdate(repositories);
        });
        realm.close();
    }


    private RealmConfiguration createRealmConfiguration() {
        return new RealmConfiguration.Builder()
                .name("wingdroid.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
    }
}
