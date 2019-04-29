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
import uk.ac.ncl.team15.android.util.DummyAttribute;

public class UserProfileAdapter extends MultiLayoutAdapter {
    private static final MultiLayoutAdapter.IGetView VP_ATTRIBUTE = new AttributeViewProvider();
    private static final MultiLayoutAdapter.IGetView VP_NOK = new NOKViewProvider();
    private static final MultiLayoutAdapter.IGetView VP_DUMMYATTRIB = new DummyAttribViewProvider();

    public UserProfileAdapter(Context context, List<Object> data) {
        super(context, data,
                new Class<?>[]{ UserAttribute.class, ModelNextOfKin.class, DummyAttribute.class }, new IGetView[]{ VP_ATTRIBUTE, VP_NOK, VP_DUMMYATTRIB });
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

            ViewHolderNOK viewHolder;

            if (convertView == null || convertView.getTag() == null || !(convertView.getTag() instanceof ViewHolderNOK)) {
                viewHolder = new ViewHolderNOK();

                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.listview_nok, parent, false);
                viewHolder.tvNokRelationship = convertView.findViewById(R.id.tvNokRelationship);
                viewHolder.tvNokName = convertView.findViewById(R.id.tvNokName);
                viewHolder.tvNokAddress = convertView.findViewById(R.id.tvNokAddress);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolderNOK) convertView.getTag();
            }

            viewHolder.tvNokRelationship.setText(modelNOK.getRelationship());
            viewHolder.tvNokName.setText(modelNOK.getFullName());
            viewHolder.tvNokAddress.setText(modelNOK.getAddress());

            return convertView;
        }

        private static class ViewHolderNOK {
            private TextView tvNokRelationship;
            private TextView tvNokName;
            private TextView tvNokAddress;
        }
    }

    private static final class DummyAttribViewProvider implements MultiLayoutAdapter.IGetView {
        @Override
        public View getView(Context context, Object obj, int position, View convertView, ViewGroup parent) {
            DummyAttribute dummyAttribute = (DummyAttribute) obj;

            ViewHolderDummyAttrib viewHolder;

            if (convertView == null || convertView.getTag() == null || !(convertView.getTag() instanceof DummyAttribute)) {
                viewHolder = new ViewHolderDummyAttrib();

                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.listview_user_attribute, parent, false);
                viewHolder.tvKey = convertView.findViewById(R.id.attribName);
                viewHolder.tvValue = convertView.findViewById(R.id.attribDesc);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolderDummyAttrib) convertView.getTag();
            }

            viewHolder.tvKey.setText(dummyAttribute.getKey());
            viewHolder.tvValue.setText(dummyAttribute.getValue());

            return convertView;
        }

        private static class ViewHolderDummyAttrib {
            private TextView tvKey;
            private TextView tvValue;
        }
    }
}
