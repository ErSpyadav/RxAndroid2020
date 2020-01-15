package demo.pushnotification.rxandroid2020;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ActivityWithCompositDisposal extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable observable = Observable.just("Sunita", "Sarayu", "Samodh", "Pradeep", "Prashant");
        DisposableObserver observerS = getObserverS();
        DisposableObserver observerR = getObserverR();
        compositeDisposable.add(
                (Disposable) observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate() {
                    @Override
                    public boolean test(Object o) throws Exception {
                        return String.valueOf(o).toLowerCase().startsWith("s");
                    }
                })
                .subscribeWith(observerS)
        );

        compositeDisposable.add(
                (Disposable) observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate() {
                    @Override
                    public boolean test(Object o) throws Exception {
                        return String.valueOf(o).toLowerCase().startsWith("p");
                    }
                })
                .subscribeWith(observerR)
        );
    }

    private DisposableObserver getObserverS() {
        return new DisposableObserver() {
            @Override
            public void onNext(Object o) {
                System.out.println("Data with S : " + (String) o);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };
    }

    private DisposableObserver getObserverR() {
        return new DisposableObserver() {
            @Override
            public void onNext(Object o) {
                System.out.println("Data with R : "+(String)o);
            }

            @Override
            public void onError(Throwable e) {

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
        compositeDisposable.clear();
    }
}
