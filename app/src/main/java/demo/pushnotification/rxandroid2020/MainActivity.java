package demo.pushnotification.rxandroid2020;

import android.app.Activity;
import android.os.Bundle;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends Activity {

    Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Observable<String> observable = Observable.just("Ram", "Shyam", "Poonam", "Sunita");
        Observer observer = getObserver();

        //subcribe observer to Observable.
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private Observer getObserver() {
        return new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("subscribe");
            }

            @Override
            public void onNext(Object o) {
                System.out.println("Data" + (String) o);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error:" + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
        System.out.println("Completed");
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();//Disposal on destroy
    }
}
