package com.example.Object.CustomizeObject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AnticipateOvershootInterpolator;

import java.util.ArrayList;

public class MyCustomItemAnimator extends DefaultItemAnimator {

    //用于存储将要移动的MoveInfo对象
    private ArrayList<MoveInfo> mPendingMoves = new ArrayList();
    //MoveInfo的临时存储集合
    private ArrayList<ArrayList<MyCustomItemAnimator.MoveInfo>> mMovesList = new ArrayList();
    //用于存储正在执行移动动画的ViewHolder
    private ArrayList<RecyclerView.ViewHolder> mMoveAnimations = new ArrayList();
    //用于存储将要被添加的ViewHolder
    private ArrayList<RecyclerView.ViewHolder> mPendingAdditions = new ArrayList();
    //存储被添加的ViewHolder的临时集合
    private ArrayList<ArrayList<RecyclerView.ViewHolder>> mAdditionsList = new ArrayList();
    //用于存储正在执行添加动画的ViewHolder
    private ArrayList<RecyclerView.ViewHolder> mAddAnimations = new ArrayList();
    //用于存储将要删除的ViewHolder
    private ArrayList<RecyclerView.ViewHolder> mPendingRemovals = new ArrayList();
    //用于存储正在执行删除动画的ViewHolder
    private ArrayList<RecyclerView.ViewHolder> mRemoveAnimations = new ArrayList();
    //用于存储将要改变的ViewHolder
    private ArrayList<MyCustomItemAnimator.ChangeInfo> mPendingChanges = new ArrayList();
    //存储被改变的ViewHolder的临时集合
    private ArrayList<ArrayList<MyCustomItemAnimator.ChangeInfo>> mChangesList = new ArrayList();
    //用于存储正在执行改变动画的ViewHolder
    private ArrayList<RecyclerView.ViewHolder> mChangeAnimations = new ArrayList();

/*private ArrayList<RecyclerView.ViewHolder> mPendingRemovals = new ArrayList<>();
    private ArrayList<RecyclerView.ViewHolder> mPendingAdditions = new ArrayList<>();
    private ArrayList<MoveInfo> mPendingMoves = new ArrayList<>();
    private ArrayList<ChangeInfo> mPendingChanges = new ArrayList<>();

    ArrayList<ArrayList<RecyclerView.ViewHolder>> mAdditionsList = new ArrayList<>();
    ArrayList<ArrayList<MoveInfo>> mMovesList = new ArrayList<>();
    ArrayList<ArrayList<ChangeInfo>> mChangesList = new ArrayList<>();

    ArrayList<RecyclerView.ViewHolder> mAddAnimations = new ArrayList<>();
    ArrayList<RecyclerView.ViewHolder> mMoveAnimations = new ArrayList<>();
    ArrayList<RecyclerView.ViewHolder> mRemoveAnimations = new ArrayList<>();
    ArrayList<RecyclerView.ViewHolder> mChangeAnimations = new ArrayList<>();*/
    //定义一个插值器：先向相反方向改变，再加速播放，会超出目标值
    private AnticipateOvershootInterpolator accelerateDecelerateInterpolator;

    @Override
    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
        return true;
    }


    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        //添加条目动画实现

        //开始重置动画
        holder.itemView.animate().setInterpolator((new ValueAnimator()).getInterpolator());
        endAnimation(holder);

        //让被添加的条目初始完全透明
        holder.itemView.setAlpha(0.0f);

        //把即将被添加的ViewHolder暂时缓存到mPendingAdditions中
        mPendingAdditions.add(holder);
        return true;
    }


    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        //移动条目动画实现

        View view = holder.itemView;
        fromX += (int)holder.itemView.getTranslationX();
        fromY += (int)holder.itemView.getTranslationY();

        //开始重置动画
        holder.itemView.animate().setInterpolator((new ValueAnimator()).getInterpolator());
        endAnimation(holder);

        int deltaX = toX - fromX;
        int deltaY = toY - fromY;
        if (deltaX == 0 && deltaY == 0) {
            dispatchMoveFinished(holder);
            return false;
        } else {
            if (deltaX != 0) {
                view.setTranslationX((float)(-deltaX));
            }

            if (deltaY != 0) {
                view.setTranslationY((float)(-deltaY));
            }

            mPendingMoves.add(new MyCustomItemAnimator.MoveInfo(holder, fromX, fromY, toX, toY));
            return true;
        }
    }

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        //删除条目动画实现

        //开始重置动画
        holder.itemView.animate().setInterpolator((new ValueAnimator()).getInterpolator());
        endAnimation(holder);

        //把将要执行删除动画的ViewHolder放入mPendingRemovals集合
        mPendingRemovals.add(holder);
        return true;
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        //改变条目动画实现

        float prevTranslationX = oldHolder.itemView.getTranslationX();
        float prevTranslationY = oldHolder.itemView.getTranslationY();
        float prevAlpha = oldHolder.itemView.getAlpha();

        //开始重置动画
        oldHolder.itemView.animate().setInterpolator((new ValueAnimator()).getInterpolator());
        endAnimation(oldHolder);

        int deltaX = (int)((float)(toX - fromX) - prevTranslationX);
        int deltaY = (int)((float)(toY - fromY) - prevTranslationY);
        oldHolder.itemView.setTranslationX(prevTranslationX);
        oldHolder.itemView.setTranslationY(prevTranslationY);
        oldHolder.itemView.setAlpha(prevAlpha);
        if (newHolder != null) {

            //开始重置动画
            newHolder.itemView.animate().setInterpolator((new ValueAnimator()).getInterpolator());
            endAnimation(newHolder);

            newHolder.itemView.setTranslationX((float)(-deltaX));
            newHolder.itemView.setTranslationY((float)(-deltaY));
            newHolder.itemView.setAlpha(0.0F);
        }

        this.mPendingChanges.add(new MyCustomItemAnimator.ChangeInfo(oldHolder, newHolder, fromX, fromY, toX, toY));
        return true;
    }

    @Override
    public void runPendingAnimations() {
        boolean removalsPending = !mPendingRemovals.isEmpty();
        boolean movesPending = !mPendingMoves.isEmpty();
        boolean changesPending = !mPendingChanges.isEmpty();
        boolean additionsPending = !mPendingAdditions.isEmpty();
        if (!removalsPending && !movesPending && !additionsPending && !changesPending) {
            // nothing to animate
            return;
        }
        // First, remove stuff
        for (RecyclerView.ViewHolder holder : mPendingRemovals) {
            animateRemoveImpl(holder);
        }
        mPendingRemovals.clear();
        // Next, move stuff
        if (movesPending) {
            final ArrayList<MoveInfo> moves = new ArrayList<>();
            moves.addAll(mPendingMoves);
            mMovesList.add(moves);
            mPendingMoves.clear();
            Runnable mover = new Runnable() {
                @Override
                public void run() {
                    for (MoveInfo moveInfo : moves) {
                        animateMoveImpl(moveInfo.holder, moveInfo.fromX, moveInfo.fromY,
                                moveInfo.toX, moveInfo.toY);
                    }
                    moves.clear();
                    mMovesList.remove(moves);
                }
            };
            if (removalsPending) {
                View view = moves.get(0).holder.itemView;
                ViewCompat.postOnAnimationDelayed(view, mover, getRemoveDuration());
            } else {
                mover.run();
            }
        }
        // Next, change stuff, to run in parallel with move animations
        if (changesPending) {
            final ArrayList<ChangeInfo> changes = new ArrayList<>();
            changes.addAll(mPendingChanges);
            mChangesList.add(changes);
            mPendingChanges.clear();
            Runnable changer = new Runnable() {
                @Override
                public void run() {
                    for (ChangeInfo change : changes) {
                        animateChangeImpl(change);
                    }
                    changes.clear();
                    mChangesList.remove(changes);
                }
            };
            if (removalsPending) {
                RecyclerView.ViewHolder holder = changes.get(0).oldHolder;
                ViewCompat.postOnAnimationDelayed(holder.itemView, changer, getRemoveDuration());
            } else {
                changer.run();
            }
        }
        // Next, add stuff
        if (additionsPending) {
            final ArrayList<RecyclerView.ViewHolder> additions = new ArrayList<>();
            additions.addAll(mPendingAdditions);
            mAdditionsList.add(additions);
            mPendingAdditions.clear();
            Runnable adder = new Runnable() {
                @Override
                public void run() {
                    for (RecyclerView.ViewHolder holder : additions) {
                        animateAddImpl(holder);
                    }
                    additions.clear();
                    mAdditionsList.remove(additions);
                }
            };
            if (removalsPending || movesPending || changesPending) {
                long removeDuration = removalsPending ? getRemoveDuration() : 0;
                long moveDuration = movesPending ? getMoveDuration() : 0;
                long changeDuration = changesPending ? getChangeDuration() : 0;
                long totalDelay = removeDuration + Math.max(moveDuration, changeDuration);
                View view = additions.get(0).itemView;
                ViewCompat.postOnAnimationDelayed(view, adder, totalDelay);
            } else {
                adder.run();
            }
        }
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {
        super.endAnimation(item);
    }

    @Override
    public boolean isRunning() {
        //用于判断动画是否正在执行
        return super.isRunning();
    }

    @Override
    public void endAnimations() {
        super.endAnimations();
    }

    /**
     * Item状态改变时的动画
     * @param changeInfo
     */
    void animateChangeImpl(final MyCustomItemAnimator.ChangeInfo changeInfo) {
        RecyclerView.ViewHolder holder = changeInfo.oldHolder;
        final View view = holder == null ? null : holder.itemView;
        RecyclerView.ViewHolder newHolder = changeInfo.newHolder;
        final View newView = newHolder != null ? newHolder.itemView : null;
        ViewPropertyAnimator newViewAnimation;
        if (view != null) {
            newViewAnimation = view.animate().setDuration(2000);
            mChangeAnimations.add(changeInfo.oldHolder);
            newViewAnimation.translationX((float)(changeInfo.toX - changeInfo.fromX));
            newViewAnimation.translationY((float)(changeInfo.toY - changeInfo.fromY));
            final ViewPropertyAnimator finalNewViewAnimation = newViewAnimation;
            newViewAnimation.alpha(0.0F).setListener(new AnimatorListenerAdapter() {
                public void onAnimationStart(Animator animator) {
                    dispatchChangeStarting(changeInfo.oldHolder, true);
                }

                public void onAnimationEnd(Animator animator) {
                    finalNewViewAnimation.setListener((Animator.AnimatorListener)null);
                    view.setAlpha(1.0F);
                    view.setTranslationX(0.0F);
                    view.setTranslationY(0.0F);
                    dispatchChangeFinished(changeInfo.oldHolder, true);
                    mChangeAnimations.remove(changeInfo.oldHolder);
                    if (!isRunning()) {
                        dispatchAnimationsFinished();
                    }
                }
            }).start();
        }

        if (newView != null) {
            newViewAnimation = newView.animate();
            this.mChangeAnimations.add(changeInfo.newHolder);
            final ViewPropertyAnimator finalNewViewAnimation1 = newViewAnimation;
            newViewAnimation
                    .setDuration(2000)
                    .alpha(1.0F)
                    .setListener(new AnimatorListenerAdapter() {
                        public void onAnimationStart(Animator animator) {
                            dispatchChangeStarting(changeInfo.newHolder, false);
                        }

                        public void onAnimationEnd(Animator animator) {
                            finalNewViewAnimation1.setListener((Animator.AnimatorListener)null);
                            newView.setAlpha(1.0F);
                            newView.setTranslationX(0.0F);
                            newView.setTranslationY(0.0F);
                            dispatchChangeFinished(changeInfo.newHolder, false);
                            mChangeAnimations.remove(changeInfo.newHolder);
                            if (!isRunning()) {
                                dispatchAnimationsFinished();
                            }
                        }
                    }).start();
        }

    }

    /**
     * Item移动实现
     * @param holder
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    void animateMoveImpl(final RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        final View view = holder.itemView;
        final int deltaX = toX - fromX;
        final int deltaY = toY - fromY;
        if (deltaX != 0) {
            view.animate().translationX(0.0F);
        }

        if (deltaY != 0) {
            view.animate().translationY(0.0F);
        }

        final ViewPropertyAnimator animation = view.animate();
        this.mMoveAnimations.add(holder);
        animation.setDuration(1000).setListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                dispatchMoveStarting(holder);
            }

            public void onAnimationCancel(Animator animator) {
                if (deltaX != 0) {
                    view.setTranslationX(0.0F);
                }

                if (deltaY != 0) {
                    view.setTranslationY(0.0F);
                }

            }

            public void onAnimationEnd(Animator animator) {
                animation.setListener((Animator.AnimatorListener)null);
                dispatchMoveFinished(holder);
                mMoveAnimations.remove(holder);
                //if (!isRunning()) {
                    dispatchAnimationsFinished();
                //}
            }
        }).start();


    }

    /**
     * 添加Item动画实现
     * @param holder
     */
    void animateAddImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        final ViewPropertyAnimator animation = view.animate();
        this.mAddAnimations.add(holder);
        animation.alpha(1.0F).setDuration(500).setListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                dispatchAddStarting(holder);
            }

            public void onAnimationCancel(Animator animator) {
                view.setAlpha(1.0F);
            }

            public void onAnimationEnd(Animator animator) {
                animation.setListener((Animator.AnimatorListener)null);
                dispatchAddFinished(holder);
                mAddAnimations.remove(holder);
                if (!isRunning()) {
                    dispatchAnimationsFinished();
                }
            }
        }).start();
    }

    /**
     * 移除Item动画实现
     * @param holder
     */
    private void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        final ViewPropertyAnimator animation = view.animate();
        mRemoveAnimations.add(holder);
        animation
                .rotationXBy(180F)
                .scaleY(0.0F)
                .scaleX(0.0F)
                .setDuration(2000).alpha(0).setListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        dispatchRemoveStarting(holder);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        animation.setListener(null);
                        view.setAlpha(1);
                        dispatchRemoveFinished(holder);
                        mRemoveAnimations.remove(holder);
                        dispatchAnimationsFinished();//dispatchFinishedWhenDone();
                    }
                }).start();
    }

    private static class MoveInfo {
        public RecyclerView.ViewHolder holder;
        public int fromX;
        public int fromY;
        public int toX;
        public int toY;

        MoveInfo(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            this.holder = holder;
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }
    }

    private static class ChangeInfo {
        public RecyclerView.ViewHolder oldHolder;
        public RecyclerView.ViewHolder newHolder;
        public int fromX;
        public int fromY;
        public int toX;
        public int toY;

        private ChangeInfo(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder) {
            this.oldHolder = oldHolder;
            this.newHolder = newHolder;
        }

        ChangeInfo(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
            this(oldHolder, newHolder);
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }

        public String toString() {
            return "ChangeInfo{oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + '}';
        }
    }
}