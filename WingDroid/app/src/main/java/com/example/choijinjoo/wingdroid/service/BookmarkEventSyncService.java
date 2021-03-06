package com.example.choijinjoo.wingdroid.service;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.example.choijinjoo.wingdroid.dao.CommitRepository;
import com.example.choijinjoo.wingdroid.dao.CommitterRepository;
import com.example.choijinjoo.wingdroid.dao.ReleaseRepository;
import com.example.choijinjoo.wingdroid.dao.RepositoryRepository;
import com.example.choijinjoo.wingdroid.dao.UserRepository;
import com.example.choijinjoo.wingdroid.model.Committer;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.User;
import com.example.choijinjoo.wingdroid.model.event.Commit;
import com.example.choijinjoo.wingdroid.model.event.Release;
import com.example.choijinjoo.wingdroid.model.response.CommitResponse;
import com.example.choijinjoo.wingdroid.source.remote.api.GithubAPI;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

/**
 * Created by choijinjoo on 2017. 8. 21..
 */

public class BookmarkEventSyncService extends JobService {
    ReleaseRepository releaseRepository;
    CommitRepository commitRepository;
    UserRepository userRepository;
    CommitterRepository committerRepository;

    @Override
    public boolean onStartJob(JobParameters params) {
        RepositoryRepository repositoryRepository = new RepositoryRepository(this);
        releaseRepository = new ReleaseRepository(this);
        commitRepository = new CommitRepository(this);
        userRepository = new UserRepository(this);
        committerRepository = new CommitterRepository(this);

        repositoryRepository.getBookmark()
                .subscribeOn(Schedulers.io())
                .subscribe(this::syncBookmarkEvents);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


    private void syncBookmarkEvents(List<Repository> repositories) {
        for (Repository repository : repositories) {
            GithubAPI.getInstance().release(repository.getAuthor(), repository.getName())
                    .subscribeOn(Schedulers.io())
                    .filter(result -> !result.isError())
                    .map(result -> result.response().body())
                    .subscribe(releases -> saveReleases(repository, releases));

            GithubAPI.getInstance().commits(repository.getAuthor(), repository.getName())
                    .subscribeOn(Schedulers.io())
                    .filter(result -> !result.isError())
                    .map(result -> result.response().body())
                    .subscribe(commits -> saveCommits(repository, commits));
        }

    }


    private void saveReleases(Repository repo, List<Release> releases) {
        if (releaseRepository.getLastRelease(repo) == null && !releases.isEmpty()) {
            Release release = releases.get(0);
            release.setRepository(repo);
            User author = userRepository.createOrUpdateUser(release.getAuthor());
            release.setAuthor(author);
            releaseRepository.createOrUpdateRelease(release);

        } else {
            Release lastRelease = releaseRepository.getLastRelease(repo);
            for (Release release : releases) {
                if (release.getPublishedAt().after(lastRelease.getPublishedAt())) {
                    release.setRepository(repo);
                    User author = userRepository.createOrUpdateUser(release.getAuthor());
                    release.setAuthor(author);
                    releaseRepository.createOrUpdateRelease(release);
                } else {
                    break;
                }
            }
        }
    }

    private void saveCommits(Repository repo, List<CommitResponse> commits) {
        if (commitRepository.getLastCommit(repo) == null && !commits.isEmpty()) {
            Commit commit = commits.get(0).getCommit();
            Committer committer = committerRepository.createOrUpdateCommitter(commit.getCommitter());
            commit.setRepository(repo);
            commit.setCommitter(committer);
            commit.setDate(committer.getDate());
            commit.setUrl(commits.get(0).getHtmlUrl());
            commitRepository.createOrUpdateCommit(commit);
        } else {
            Commit lastCommit = commitRepository.getLastCommit(repo);
            for (CommitResponse commitResponse : commits) {
                if (commitResponse.getCommit().getCommitter().getDate().after(lastCommit.getCommitter().getDate())) {
                    Committer committer = committerRepository.createOrUpdateCommitter(commitResponse.getCommit().getCommitter());
                    commitResponse.getCommit().setRepository(repo);
                    commitResponse.getCommit().setCommitter(committer);
                    commitResponse.getCommit().setDate(committer.getDate());
                    commitRepository.createOrUpdateCommit(commitResponse.getCommit());
                } else {
                    break;
                }
            }

        }
    }
}