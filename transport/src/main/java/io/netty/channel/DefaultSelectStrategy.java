/*
 * Copyright 2016 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel;

import io.netty.util.IntSupplier;

/**
 * Default select strategy.
 */
final class DefaultSelectStrategy implements SelectStrategy {
    static final SelectStrategy INSTANCE = new DefaultSelectStrategy();

    private DefaultSelectStrategy() { }

    /**
     * 如果当前的EventLoop中有待处理的任务，那么会调用selectSupplier.get()方法，也就是最终会调用Selector.selectNow()方法，并清空selectionKeys。
     * Selector.selectNow()方法不会发生阻塞，如果没有一个channel(即，该channel注册的事件发生了)被选择也会立即返回，否则返回就绪I/O事件的个数。
     * 如果当前的EventLoop中没有待处理的任务，那么返回’SelectStrategy.SELECT(即，-1)’。
     */
    @Override
    public int calculateStrategy(IntSupplier selectSupplier, boolean hasTasks) throws Exception {
        return hasTasks ? selectSupplier.get() : SelectStrategy.SELECT;
    }
}
