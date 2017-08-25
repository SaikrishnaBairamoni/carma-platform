/*
 * TODO: Copyright (C) 2017 LEIDOS.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package gov.dot.fhwa.saxton.carma.guidance.pubsub;

import org.ros.message.MessageListener;
import org.ros.node.topic.Subscriber;

public class RosSubscriber<T> implements ISubscriber<T> {
    protected Subscriber<T> subscriber;
    protected RosSubscriptionChannel<T> parent;
    protected T lastMessage = null;

    RosSubscriber(Subscriber<T> subscriber, RosSubscriptionChannel<T> parent) {
        this.subscriber = subscriber;
        this.parent = parent;
        subscriber.addMessageListener(new MessageListener<T>() {
            @Override public void onNewMessage(T t) {
                lastMessage = t;
            }
        });
    }

    @Override public T getLastMessage() {
        return lastMessage;
    }

    @Override public void registerOnMessageCallback(final OnMessageCallback<T> callback) {
        subscriber.addMessageListener(new MessageListener<T>() {
            @Override public void onNewMessage(T t) {
                callback.onMessage(t);
            }
        });
    }

    @Override public void close() {
        parent.notifyClientShutdown();
    }
}
