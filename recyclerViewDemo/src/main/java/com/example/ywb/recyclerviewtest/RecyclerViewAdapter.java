package com.example.ywb.recyclerviewtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 2016/2/23.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int PULL_TO_MORE = 0;
    public static final int PULLINT_MORE = 1;
    public final static int PULLED_FINISH = 2;
    private final int TYPE_ITEM = 0;
    private final int TYPE_FOOTER = 1;

    private int current_load_more_state = 0;
    private Context mContext;
    private LayoutInflater mInflater;
    public List<String> mDatas;

    //子view是否充满了手机屏幕
    private boolean isCompleteFill = false;


    public interface OnRecyclerViewItemListener {
        public void onItemClickListener(View view,int position);
        public void onItemLongClickListener(View view, int position);
    }

    private OnRecyclerViewItemListener mOnRecyclerViewItemListener;

    public void setOnRecyclerViewItemListener(OnRecyclerViewItemListener listener){
        mOnRecyclerViewItemListener = listener;
    }

    public RecyclerViewAdapter(Context context, List<String> datas) {

        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder == null) {
            return;
        }
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).textView.setText(mDatas.get(position));

            onItemSonViewClick(holder);
            if (mOnRecyclerViewItemListener != null){
                itemOnClick(holder);
                itemOnLongClick(holder);
            }
        } else if (holder instanceof FooterViewHolder) {
            String msg = "";
            switch (current_load_more_state) {
                case PULL_TO_MORE:
                    msg = "上拉加载更多";
                    break;
                case PULLINT_MORE:
                    msg = "正在加载...";
                    break;
                case PULLED_FINISH:
                    current_load_more_state = 0;
                    msg = "上拉加载更多";
                    break;
                default:
                    break;
            }
            ((FooterViewHolder) holder).footerTextView.setText(msg);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //普通item的view
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item, parent, false);
            ItemViewHolder mItemViewHolder = new ItemViewHolder(view);
            return mItemViewHolder;
        } else if (viewType == TYPE_FOOTER && isCompleteFill) {
            View view = mInflater.inflate(R.layout.add_more_footer, parent, false);
            FooterViewHolder mFooterViewHolder = new FooterViewHolder(view);
            return mFooterViewHolder;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if (isCompleteFill) {
            return mDatas.size() + 1;
        } else {
            return mDatas.size();
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (!isCompleteFill || position + 1 != getItemCount()) {
            return TYPE_ITEM;
        } else {
            return TYPE_FOOTER;
        }
    }

    //下拉刷新时，添加数据
    public void addItems(List<String> newDatas) {
        if (newDatas != null) {
            newDatas.addAll(mDatas);
            mDatas.removeAll(mDatas);
            mDatas.addAll(newDatas);
            notifyDataSetChanged();
        }
    }

    //加载更多时，添加数据
    public void addMoreDatas(List<String> moreDatas) {
        if (moreDatas != null) {
            mDatas.addAll(moreDatas);
            current_load_more_state = PULLED_FINISH;
            notifyDataSetChanged();
        }
    }

    private void itemOnClick(final RecyclerView.ViewHolder holder){
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                mOnRecyclerViewItemListener.onItemClickListener(holder.itemView, position);
            }
        });
    }

    private void itemOnLongClick(final RecyclerView.ViewHolder holder){
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = holder.getLayoutPosition();
                mOnRecyclerViewItemListener.onItemLongClickListener(holder.itemView, position);

                //返回true是为了防止触发onClick事件
                return true;
            }
        });
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        protected TextView textView;
        protected Button button;

        public ItemViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.text_view);
            button = (Button) itemView.findViewById(R.id.button);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        private TextView footerTextView;

        public FooterViewHolder(View itemView) {
            super(itemView);

            footerTextView = (TextView) itemView.findViewById(R.id.add_more_state_text);
        }
    }


    public void changeLoadMoreStatus(int status) {
        current_load_more_state = status;
        notifyDataSetChanged();
    }

    public void changeIsCompleteFill(boolean status) {
        isCompleteFill = status;
        notifyDataSetChanged();
    }

    private void onItemSonViewClick(final RecyclerView.ViewHolder holder){
        ((ItemViewHolder)holder).button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                Toast.makeText(mContext, "按钮：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void insertData(String data,int position){
        mDatas.add(position,data);
        notifyDataSetChanged();
        notifyItemInserted(position);
    }

    public void removeData(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);
    }
}


