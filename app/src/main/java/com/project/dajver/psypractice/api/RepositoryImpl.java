package com.project.dajver.psypractice.api;

import android.util.Log;

import com.project.dajver.psypractice.App;
import com.project.dajver.psypractice.api.models.NewsModel;
import com.project.dajver.psypractice.api.models.SearchModel;
import com.project.dajver.psypractice.api.models.VideosModel;
import com.project.dajver.psypractice.ui.favorite.db.DatabaseHelper;
import com.project.dajver.psypractice.ui.favorite.db.model.FavoriteNewsModel;
import com.project.dajver.psypractice.api.models.NewsDetailsModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static com.project.dajver.psypractice.etc.Constants.BASE_URL;
import static com.project.dajver.psypractice.etc.Constants.LINK_PUBLICATIONS;

/**
 * Created by gleb on 11/13/17.
 */

public class RepositoryImpl implements IRepository {

    private ArrayList<NewsModel> newsModels = new ArrayList<>();
    private ArrayList<SearchModel> searchModels = new ArrayList<>();
    private ArrayList<VideosModel> videosModels = new ArrayList<>();

    @Override
    public Observable<ArrayList<NewsModel>> getLastNews(final String urlLink) {
        return Observable.create(new ObservableOnSubscribe<ArrayList<NewsModel>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<NewsModel>> observableEmitter) throws Exception {
                Document doc = null;
                try {
                    Log.e("NEWS LINK", urlLink);
                    doc = Jsoup.connect(urlLink).get();

                    Elements titleElement = doc.getElementsByClass("newslesttitle");
                    Elements descriptionElement = doc.getElementsByClass("ptext");
                    Elements imageElement = doc.getElementsByClass("preview_picture");
                    Elements linkElement = doc.getElementsByClass("uk-width-1-3");

                    for(int i = 0; i < titleElement.size(); i++) {
                        Elements ahref = imageElement.get(i).select("a[href]");
                        String style = ahref.attr("style");

                        Elements articleLink = linkElement.get(i).select("a");
                        String url = articleLink.attr("href");

                        String titleText = titleElement.get(i).text();
                        String descriptionText = descriptionElement.get(i).text();
                        String title = titleText.replaceAll(String.valueOf(getViewsCount(titleElement.get(i).text())), "");

                        NewsModel model = new NewsModel(title,
                                descriptionText,
                                getImageLink(style),
                                url,
                                getViewsCount(titleText));
                        newsModels.add(model);
                    }
                    observableEmitter.onNext(newsModels);
                } catch (IOException e) {
                    observableEmitter.onError(e);
                } finally {
                    observableEmitter.onComplete();
                }
            }
        });
    }

    @Override
    public Observable<ArrayList<SearchModel>> getSearchData(final String urlLink) {
        return Observable.create(new ObservableOnSubscribe<ArrayList<SearchModel>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<SearchModel>> observableEmitter) throws Exception {
                Document doc = null;
                try {
                    Log.e("SEARCH LINK", urlLink);
                    doc = Jsoup.connect(urlLink).get();

                    Elements titleElement = doc.getElementsByClass("class 234");
                    Elements descriptionElement = doc.select("td").select("p");
                    Elements linkElement = doc.getElementsByClass("class 234");

                    for(int i = 0; i < titleElement.size(); i++) {
                        Elements articleLink = linkElement.get(i).select("a");
                        String url = articleLink.attr("href");

                        String titleText = titleElement.get(i).text();
                        String descriptionText = descriptionElement.get(i).text();

                        if(url.contains(LINK_PUBLICATIONS)) {
                            SearchModel model = new SearchModel(titleText,
                                    descriptionText,
                                    url);
                            searchModels.add(model);
                        }
                    }
                    observableEmitter.onNext(searchModels);
                } catch (IOException e) {
                    observableEmitter.onError(e);
                } finally {
                    observableEmitter.onComplete();
                }
            }
        });
    }

    @Override
    public Observable<ArrayList<VideosModel>> getVideos(final String urlLink) {
        return Observable.create(new ObservableOnSubscribe<ArrayList<VideosModel>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<VideosModel>> observableEmitter) throws Exception {
                Document doc = null;
                try {
                    Log.e("VIDEOS LINK", urlLink);
                    doc = Jsoup.connect(urlLink).get();

                    Elements mainElement = doc.getElementsByClass("newslesttitle-tab uk-h4");
                    Elements imageElement = doc.getElementsByClass("uk-panel-box");

                    for(int i = 0; i < mainElement.size(); i++) {
                        String titleText = mainElement.get(i).text();

                        Elements videoLink = mainElement.get(i).select("a");
                        String url = videoLink.attr("href");

                        Elements imageLink = imageElement.get(i).select("img");
                        String imageUrl = imageLink.attr("src");

                        if(url.contains("useful")) {
                            VideosModel model = new VideosModel(titleText, imageUrl, BASE_URL + url);
                            videosModels.add(model);
                        }
                    }
                    observableEmitter.onNext(videosModels);
                } catch (IOException e) {
                    observableEmitter.onError(e);
                } finally {
                    observableEmitter.onComplete();
                }
            }
        });
    }

    @Override
    public Observable<List<FavoriteNewsModel>> getFavorites() {
        return Observable.create(new ObservableOnSubscribe<List<FavoriteNewsModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<FavoriteNewsModel>> observableEmitter) throws Exception {
                try {
                    DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();
                    observableEmitter.onNext(databaseHelper.getFavoriteDao().getAllFavorites());
                } finally {
                    observableEmitter.onComplete();
                }
            }
        });
    }

    @Override
    public Observable<NewsDetailsModel> getNewsDetails(final String urlLink) {
        return Observable.create(new ObservableOnSubscribe<NewsDetailsModel>() {
            @Override
            public void subscribe(ObservableEmitter<NewsDetailsModel> observableEmitter) throws Exception {
                NewsDetailsModel newsModels = null;
                Document doc;
                try {
                    Log.e("DETAILS LINK", urlLink);
                    doc = Jsoup.connect(urlLink).get();

                    Element h1 = doc.select("h1").first();
                    Element titleElement = h1.getElementsByClass("uk-text-center").first();
                    Element descriptionElement = doc.getElementById("newsdettext");
                    Element img = doc.select("img").first();
                    Element date = doc.getElementsByClass("avtoprdate uk-margin-top uk-text-center uk-margin-bottom").first();

                    newsModels = new NewsDetailsModel();
                    newsModels.setTitle(titleElement.text());
                    newsModels.setDescription(descriptionElement.html());
                    newsModels.setImageUrl(BASE_URL + img.attr("src"));
                    newsModels.setDate(date.text());

                    observableEmitter.onNext(newsModels);
                } catch (IOException e) {
                    observableEmitter.onError(e);
                } finally {
                    observableEmitter.onComplete();
                }
            }
        });
    }

    @Override
    public Observable<String> getVideoDetails(final String linkUrl) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                Document doc = null;
                try {
                    Log.e("VIDEO LINK", linkUrl);
                    doc = Jsoup.connect(linkUrl).get();

                    Element linkElement = doc.getElementsByClass("news-detail").first();
                    Element articleLink = linkElement.select("iframe").first();
                    String url = articleLink.attr("src");

                    String youtube = "https://www.youtube.com/watch?v=";
                    String videoUrl = youtube + extractYoutubeVideoId(url);
                    observableEmitter.onNext(videoUrl);
                } catch (IOException e) {
                    observableEmitter.onError(e);
                } finally {
                    observableEmitter.onComplete();
                }
            }
        });
    }

    private String getImageLink(String str) {
        Pattern pattern = Pattern.compile("url\\('(.+?)'\\)");
        Matcher matcher = pattern.matcher(str);
        matcher.find();
        return BASE_URL + matcher.group(1);
    }

    private int getViewsCount(String input) {
        int lastNumberInt = 0;
        Pattern lastIntPattern = Pattern.compile("[^0-9]+([0-9]+)$");
        Matcher matcher = lastIntPattern.matcher(input);
        if (matcher.find()) {
            String someNumberStr = matcher.group(1);
            lastNumberInt = Integer.parseInt(someNumberStr);
        }
        return lastNumberInt;
    }

    public String extractYoutubeVideoId(String ytUrl) {
        String videoId = null;
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(ytUrl);
        if(matcher.find()){
            videoId= matcher.group();
        }
        return videoId;
    }
}
