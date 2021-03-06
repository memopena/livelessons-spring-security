/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.messages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * @author Rob Winch
 */
@Controller
@RequestMapping("/messages")
public class MessageController {
	private final MessageRepository messages;

	public MessageController(MessageRepository messages) {
		this.messages = messages;
	}

	@GetMapping("/")
	public String index() {
		return "redirect:inbox";
	}

	@GetMapping("/inbox")
	public String inbox(Map<String, Object> model) {
		Iterable<Message> inboxMessages = this.messages.findAll();
		model.put("messages", inboxMessages);
		return "messages/inbox";
	}

	@GetMapping("/{id}")
	public String view(@PathVariable Long id, Map<String, Object> model) {
		Optional<Message> message = this.messages.findById(id);
		model.put("message", message.orElse(null));
		return "messages/view";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id) {
		this.messages.deleteById(id);
		return "redirect:inbox?deleted";
	}
}
