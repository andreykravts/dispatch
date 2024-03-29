package dev.lydtech.dispatch.handler;

import dev.lydtech.dispatch.message.OrderCreated;
import dev.lydtech.dispatch.service.DispatchService;
import dev.lydtech.dispatch.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

class OrderCreatedHandlerTest {
    private OrderCreatedHandler handler;
    private DispatchService dispatchServiceMock;

    @BeforeEach
    void setUp() {
        dispatchServiceMock=mock(DispatchService.class);
        handler = new OrderCreatedHandler(dispatchServiceMock);
    }

    @Test
    void listen_Success() throws Exception {
        OrderCreated testEvent = TestEventData.builderOrderCreatedEvent(randomUUID(),randomUUID().toString());
        handler.listen(testEvent);
        verify(dispatchServiceMock, times(1)).process(testEvent);
    }

    @Test
    void listen_SuccessThrowsException() throws Exception {
        OrderCreated testEvent = TestEventData.builderOrderCreatedEvent(randomUUID(),randomUUID().toString());

        doThrow(new RuntimeException("Service failure")).when(dispatchServiceMock).process(testEvent );

        handler.listen(testEvent);
        verify(dispatchServiceMock, times(1)).process(testEvent);
    }
}