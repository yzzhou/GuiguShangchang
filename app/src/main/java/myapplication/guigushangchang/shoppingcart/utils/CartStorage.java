package myapplication.guigushangchang.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import myapplication.guigushangchang.home.bean.GoodsBean;

/**
 * Created by zhouzhou on 2017/6/13.
 */

public class CartStorage {
    public static final String JSON_CART = "json_cart";
    private static  CartStorage cartStorage;
    private static Context mContext;

    private SparseArray<GoodsBean> sparseArray;

    private CartStorage(Context context) {
        this.mContext = context;
        sparseArray = new SparseArray<>();
        listToSpare();
    }

    /**
     * List数据转SparseArray
     */
    private void listToSpare() {
        List<GoodsBean> list= getAllData();
        if(list != null  && list.size()  > 0){
            for (int i = 0 ;i < list.size(); i++){
                GoodsBean goodsBean = list.get(i);
                sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
            }
        }
    }

    /**
     * 得到所有数据
     * @return
     */
    public List<GoodsBean> getAllData() {
        return getDataFromLocal();
    }

    /**
     * 得到本地保持的数据
     * @return
     */
    private List<GoodsBean> getDataFromLocal() {
        List<GoodsBean> list  = new ArrayList<>();
        //得到保持的数据
        String saveJson = CacheUtils.getString(mContext,JSON_CART);
        if(!TextUtils.isEmpty(saveJson)){
            list = new Gson().fromJson(saveJson,new TypeToken<List<GoodsBean>>(){}.getType());
        }
        return list;
    }

    /**
     * 得到CartStorage实例
     * @param context
     * @return
     */
    public static CartStorage getInstance(Context context){

        if(cartStorage == null){
            mContext = context;
            synchronized (CartStorage.class){
                if(cartStorage == null){
                    cartStorage = new CartStorage(context);
                }
            }
        }
        return  cartStorage;
    }

    /**
     * 增加数据
     * @param goodsBean
     */
    public void addData(GoodsBean goodsBean){
        //1.增加数据
        GoodsBean temp = sparseArray.get(Integer.valueOf(goodsBean.getProduct_id()));
        if(temp != null){
            //存在
            temp.setNumber(temp.getNumber() + goodsBean.getNumber());
        }else {
            temp = goodsBean;
            //至少设置1个
            temp.setNumber(1);
        }

        //添加
        sparseArray.put(Integer.parseInt(temp.getProduct_id()),temp);


        //2.保持数据到本地
        commit();
    }

    /**
     * 修改数据
     * @param goodsBean
     */
    public void updateData(GoodsBean goodsBean){
        //1.修改数据

        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);

        //2.保持数据到本地
        commit();
    }

    /**
     * 删除数据
     * @param goodsBean
     */
    public void deleteData(GoodsBean goodsBean){
        //1.删除数据
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));

        //2.保持数据到本地
        commit();
    }

    /**
     * 保持数据
     */
    private void commit() {
        List<GoodsBean> list = sparseArrayToList();
        String json = new Gson().toJson(list);
        CacheUtils.putString(mContext,JSON_CART,json);

    }

    /**
     * sparseArray转List数据
     * @return
     */
    private List<GoodsBean> sparseArrayToList() {
        List<GoodsBean> list = new ArrayList<>();
        if (sparseArray != null && sparseArray.size() > 0) {
            for (int i = 0; i < sparseArray.size(); i++) {
                GoodsBean shoppingCart = sparseArray.valueAt(i);
                list.add(shoppingCart);
            }
        }
        return list;
    }


//    public GoodsBean findDete(String product_id) {
//        GoodsBean goodsBean = sparseArray.get(Integer.parseInt(product_id));
//        return goodsBean;
//    }


}
