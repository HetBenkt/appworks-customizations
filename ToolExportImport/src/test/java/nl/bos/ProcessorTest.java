package nl.bos;

import nl.bos.action.IProcessor;
import nl.bos.item.Entity;
import nl.bos.item.IItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Processor.class, Storage.class})
public class ProcessorTest {

    @Mock
    private Storage storage;
    @Mock
    private Entity entity;
    @Mock
    private URL url;
    @Mock
    private URLConnection urlConnection;

    @Test
    public void execute() throws Exception {
        PowerMockito.spy(Storage.class);
        PowerMockito.doReturn(storage).when(Storage.class);
        Storage.getInstance();
        PowerMockito.when(storage.createFile(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(Paths.get("C:\\export\\1"));
        PowerMockito.when(entity.getContents()).thenReturn(Collections.singletonList("content_dummy"));
        PowerMockito.whenNew(URL.class).withAnyArguments().thenReturn(url);
        PowerMockito.when(url.openConnection()).thenReturn(urlConnection);
        PowerMockito.when(urlConnection.getContentType()).thenReturn("text/plain");
        PowerMockito.when(entity.getItemId()).thenReturn("1001");
        PowerMockito.when(entity.getContentIds()).thenReturn(Collections.singletonList("123456789"));

        IProcessor processor = new Processor();
        List<IItem> items = new ArrayList<>();
        items.add(entity);
        processor.execute(items, "1");

        Mockito.verify(storage, times(1)).writeMetadata(Matchers.any(IItem.class));
        Mockito.verify(storage, times(1)).writeContent(Matchers.any(IItem.class), Matchers.anyString());
    }
}