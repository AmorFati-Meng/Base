package jiemi.com.base.fragment;

import java.util.HashMap;

/**
 * 类名：
 * <p/>
 * 描述：
 *
 * @author：NIU Date：2016/7/18
 */
public class FragmentFactory  {
    private static  BaseFragment fragment;

    private static HashMap<Integer,BaseFragment> mHashMapFragments=new HashMap<Integer,BaseFragment>();


    public static  BaseFragment createFragment(int position){
        fragment=mHashMapFragments.get(position);
        if(fragment==null){
            switch (position){
              /*  case:
                break;*/
            }
           mHashMapFragments.put(position,fragment);
        }
      return  fragment;
    }
}
