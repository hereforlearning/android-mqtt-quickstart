package io.bytehala.eclipsemqtt.sample.newconnection

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.bytehala.eclipsemqtt.sample.ActivityConstants
import io.bytehala.eclipsemqtt.sample.Notify
import io.bytehala.eclipsemqtt.sample.R
import kotlinx.android.synthetic.main.old_activity_new_connection.*

class NewConnectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.old_activity_new_connection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectButton.setOnClickListener {
//            //extract client information
//            Intent dataBundle = new Intent();
            val server = serverURI.text.toString()
            val port = port.text.toString()
            val clientId = clientId.text.toString()

            if ((server.isEmpty() || port.isEmpty() || clientId.isEmpty())) {
                val notificationText = getString(R.string.missingOptions)
                Notify.toast(requireContext(), notificationText, Toast.LENGTH_LONG)
                return@setOnClickListener
            }

            val cleanSession = cleanSessionCheckBox.isChecked


            val result = Bundle()

//            //put data into a bundle to be passed back to ClientConnections
            result.putString(ActivityConstants.server, server)
            result.putString(ActivityConstants.port, port)
            result.putString(ActivityConstants.clientId, clientId)
            result.putInt(ActivityConstants.action, ActivityConstants.connect)
            result.putBoolean(ActivityConstants.cleanSession, cleanSession)

            /**
             * Watch out, these settings are for when Advanced settings are empty
             */
            result.putString(ActivityConstants.message, ActivityConstants.empty)
            result.putString(ActivityConstants.message, ActivityConstants.empty)
            result.putString(ActivityConstants.topic, ActivityConstants.empty)
            result.putInt(ActivityConstants.qos, ActivityConstants.defaultQos)
            result.putBoolean(ActivityConstants.retained, ActivityConstants.defaultRetained)

            result.putString(ActivityConstants.username, ActivityConstants.empty)
            result.putString(ActivityConstants.password, ActivityConstants.empty)

            result.putInt(ActivityConstants.timeout, ActivityConstants.defaultTimeOut)
            result.putInt(ActivityConstants.keepalive, ActivityConstants.defaultKeepAlive)
            result.putBoolean(ActivityConstants.ssl, ActivityConstants.defaultSsl)

            val actionConnect = NewConnectionFragmentDirections.actionConnect(result)
            findNavController().navigate(actionConnect)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.activity_new_connection, menu)
        /*
        public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_new_connection, menu);
    menu.findItem(R.id.connectActionDeprecated).setOnMenuItemClickListener(listener);

    return true;
  }
         */
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //start the advanced options activity
        val dataBundle = Intent()
        dataBundle.setClassName(
            requireContext(),
            "io.bytehala.eclipsemqtt.sample.newconnection.AdvancedActivity"
        )
        startActivityForResult(
            dataBundle,
            ActivityConstants.advancedConnect
        )
        return super.onOptionsItemSelected(item)
    }

}
