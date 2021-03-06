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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import sample.messages.webdriver.InboxPage;
import sample.messages.webdriver.IndexPage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class InsecureApplicationTests {
	@Autowired
	private WebDriver driver;

	@Test
	public void indexRedirects() {
		InboxPage inbox = IndexPage.to(this.driver, InboxPage.class);
		inbox.assertAt();
	}

	@Test
	public void inboxLoads() {
		InboxPage inbox = InboxPage.to(this.driver, InboxPage.class);
		inbox.assertAt();
	}

	@Test
	public void messages() {
		InboxPage inbox = InboxPage.to(this.driver, InboxPage.class);
		List<InboxPage.Message> messages = inbox.messages();
		assertThat(messages).hasSize(4);
		InboxPage.Message message0 = messages.get(0);
		assertThat(message0.getFrom()).isEqualTo("josh@example.com");
		assertThat(message0.getText()).isEqualTo("This message is for Rob");
	}

	@Test
	@DirtiesContext
	public void deleteWorks() {
		InboxPage inbox = InboxPage.to(this.driver, InboxPage.class);
		InboxPage.Message toDelete = inbox.messages().get(0);

		inbox = toDelete.delete();
		inbox.assertDeleteSuccess();
		InboxPage.Message message0 = inbox.messages().get(0);

		assertThat(inbox.messages()).hasSize(3);
		assertThat(message0.getText()).isNotEqualTo("This message is for Rob");
	}
}
