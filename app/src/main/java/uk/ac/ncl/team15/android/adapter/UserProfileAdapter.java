package uk.ac.ncl.team15.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uk.ac.ncl.team15.android.R;
import uk.ac.ncl.team15.android.UserAttribute;
import uk.ac.ncl.team15.android.retrofit.models.ModelNextOfKin;

public class UserProfileAdapter extends MultiLayoutAdapter {
    private static final MultiLayoutAdapter.IGetView VP_ATTRIBUTE = new AttributeViewProvider();
    private static final MultiLayoutAdapter.IGetView VP_NOK = new NOKViewProvider();

    public UserProfileAdapter(Context context, List<Object> data) {
        super(context, data,
                new Class<?>[]{ UserAttribute.class, ModelNextOfKin.class }, new IGetView[]{ VP_ATTRIBUTE, VP_NOK });
    }

    private static final class AttributeViewProvider implements MultiLayoutAdapter.IGetView {
        @Override
        public View getView(Context context, Object obj, int position, View convertView, ViewGroup parent) {
            UserAttribute attrib = (UserAttribute) obj;

            ViewHolderAttribute viewHolder;

            if (convertView == null || convertView.getTag() == null || !(convertView.getTag() instanceof ViewHolderAttribute)) {
                viewHolder = new ViewHolderAttribute();

                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.listview_user_attribute, parent, false);
                viewHolder.tvKey = convertView.findViewById(R.id.attribName);
                viewHolder.tvValue = convertView.findViewById(R.id.attribDesc);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolderAttribute) convertView.getTag();
            }

            viewHolder.tvKey.setText(attrib.getKey());
            viewHolder.tvValue.setText(attrib.getValue());

            return convertView;
        }

        private static class ViewHolderAttribute {
            private TextView tvKey;
            private TextView tvValue;
            private TextView imgAction;
        }
    }

    private static final class NOKViewProvider implements MultiLayoutAdapter.IGetView {
        @Override
        public View getView(Context context, Object obj, int position, View convertView, ViewGroup parent) {
            ModelNextOfKin modelNOK = (ModelNextOfKin) obj;

            AttributeViewProvider.ViewHolderAttribute viewHolder;

            if (convertView == null || convertView.getTag() == null || !(convertView.getTag() instanceof AttributeViewProvider.ViewHolderAttribute)) {
                viewHolder = new AttributeViewProvider.ViewHolderAttribute();

                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.listview_user_attribute, parent, false);
                viewHolder.tvKey = convertView.findViewById(R.id.attribName);
                viewHolder.tvValue = convertView.findViewById(R.id.attribDesc);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (AttributeViewProvider.ViewHolderAttribute) convertView.getTag();
            }

            viewHolder.tvKey.setText(modelNOK.getFirstName());
            viewHolder.tvValue.setText(modelNOK.getRelationship());

            return convertView;
        }

        private static class ViewHolderAttribute {
            private TextView tvKey;
            private TextView tvValue;
            private TextView imgAction;
        }

        private static class ViewHolderNOK {
            private TextView tvKey;
            private TextView tvValue;
            private TextView imgAction;
        }
    }
}
